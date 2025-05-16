package com.xixi.fault.tolerant;

import com.xixi.model.RpcRequest;
import com.xixi.model.RpcResponse;
import com.xixi.model.ServiceMetaInfo;
import com.xixi.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 转移到其他服务节点 - 容错策略
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.getOrDefault
                ("serviceMetaInfoList", new ArrayList<>());
        if(serviceMetaInfoList.isEmpty()) {
           throw new RuntimeException("暂无可用服务节点（FailOver）");
        }
        // 转移到其他服务节点
        RpcRequest rpcRequest = (RpcRequest) context.getOrDefault("rpcRequest", null);
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            try {
                return VertxTcpClient.doRequest(rpcRequest,serviceMetaInfo);
            } catch (Exception err) {
                serviceMetaInfoList.remove(serviceMetaInfo);
                //再次转移到其他服务节点并删除当前节点
                log.info("转移到其他服务节点（FailOver）",err);
                context.put("serviceMetaInfoList",serviceMetaInfoList);
                return doTolerant(context,e);
            }
        }
        return null;
    }
}

