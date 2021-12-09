package com.wmapp.common.enums;

/**
 * redis常量类
 */
public enum KConstants {
    /**
     * 动态参数配置
     */
    DYNAMIC_CONFIG("fil:dy_conf"),
    /**
     * 节点用户
     */
    NODE_USER_LIST("fil:node_u"),
    /**
     * 节点用户收益list
     */
    NODE_USER_REVENUE_LIST("fil:node_u_rev");

    private final String key;

    KConstants(String key) {
        this.key = key;
    }
    @Override
    public String toString() {
        return key;
    }
}
