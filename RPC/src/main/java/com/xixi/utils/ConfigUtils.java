package com.xixi.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;

/**
 * 配置工具类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".yml");
        Dict dict = YamlUtil.loadByPath(configFileBuilder.toString());
        //如果yml配置文件不存在，使用默认配置
        if (dict == null) {
            configFileBuilder = new StringBuilder("application.properties");
            Props props = new Props(configFileBuilder.toString());
            return props.toBean(tClass, prefix);
        }
        return BeanUtil.copyProperties(dict.getBean(prefix), tClass);
//        Props props = new Props(configFileBuilder.toString());
//        return props.toBean(tClass, prefix);
    }
}
