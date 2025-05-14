package com.xixi.register;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.xixi.RpcApplication;
import com.xixi.config.RegisterConfig;
import com.xixi.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EtcdRegister implements Register {

    //本地注册的节点信息（用于维护心跳）
    private static final Set<String> LocalRegisterNodeKeySet = new HashSet<>();

    private static boolean schedulerStarted = false;

    private Client client;

    private KV kvClient;

    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegisterConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                // 添加权威设置解决HTTP/2协议问题
                .authority(registryConfig.getAddress().replace("http://", ""))
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
        //开启心跳检测
        heartbeat();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        // 创建 Lease 和 KV 客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建一个 默认30 秒的租约
        long leaseId = leaseClient.grant(RpcApplication.getRpcConfig().getRegisterConfig().getTimeout()).get().getID();

        // 设置要存储的键值对
        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

        // 将键值对与租约关联起来，并设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();

        //本地存储
        LocalRegisterNodeKeySet.add(registerKey);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
        //本地删除
        LocalRegisterNodeKeySet.remove(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey());
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 前缀搜索，结尾一定要加 '/'
        String searchPrefix = ETCD_ROOT_PATH + serviceKey + "/";

        try {
            // 前缀查询
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(
                            ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                            getOption)
                    .get()
                    .getKvs();
            // 解析服务信息
            return keyValues.stream()
                    .map(keyValue -> {
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(value, ServiceMetaInfo.class);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        // 释放资源
        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void heartbeat() {
        if (schedulerStarted) {
            CronUtil.stop();
            schedulerStarted = false;
        }
        //10s执行一次
        CronUtil.schedule("*/10 * * * * ?", new Task() {
            @Override
            public void execute() {
                for(String key : LocalRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValues = kvClient.get(
                                        ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();
                        if(CollUtil.isEmpty(keyValues)) {
                            continue;
                        }
                        KeyValue keyValue = keyValues.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                        register(serviceMetaInfo);
                    } catch (Exception e) {
                        throw new RuntimeException(key+"续签失败",e);
                    }
                }
            }
        });
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        schedulerStarted = true;
    }
}

