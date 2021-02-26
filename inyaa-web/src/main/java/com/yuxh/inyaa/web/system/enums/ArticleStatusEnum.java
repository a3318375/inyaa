package com.yuxh.inyaa.web.system.enums;

import java.util.HashMap;

/**
 * @author: byteblogs
 * @date: 2019/09/02 16:35
 */
public enum ArticleStatusEnum {

    /**
     * 草稿
     */
    DRAFT(1, "draft"),

    /**
     * 发布
     */
    PUBLISH(2, "publish");

    private static final java.util.Map<Integer, ArticleStatusEnum> enumTypeMap = new HashMap<>();

    static {
        for (ArticleStatusEnum roleEnum : ArticleStatusEnum.values()) {
            enumTypeMap.put(roleEnum.getStatus(), roleEnum);
        }
    }

    private final Integer status;

    private final String name;

    ArticleStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static java.util.Map<Integer, ArticleStatusEnum> getEnumTypeMap() {
        return enumTypeMap;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
