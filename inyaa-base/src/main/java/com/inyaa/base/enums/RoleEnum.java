package com.inyaa.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yuxh
 * @date: 2021/03/01 16:35:01
 */
public enum RoleEnum {

    /**
     * 普通用户
     */
    USER(1, "user"),

    /**
     * 管理员
     */
    ADMIN(2, "admin");

    private static final Map<Integer, RoleEnum> enumTypeMap = new HashMap<>();

    static {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            enumTypeMap.put(roleEnum.getRoleId(), roleEnum);
        }
    }

    private final Integer roleId;

    private final String roleName;

    RoleEnum(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static Map<Integer, RoleEnum> getEnumTypeMap() {
        return enumTypeMap;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

}
