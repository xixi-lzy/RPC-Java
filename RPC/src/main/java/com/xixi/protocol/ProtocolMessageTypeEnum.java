package com.xixi.protocol;

import lombok.Getter;

@Getter
public enum ProtocolMessageTypeEnum {

    REQUEST(0),
    RESPONSE(1),
    HEARTBEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     *
     * @param key
     * @return
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum e : ProtocolMessageTypeEnum.values()) {
            if (e.key == key) {
                return e;
            }
        }
        return null;
    }
}
