package com.inyaa.web.auth.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/3 23:59
 */
@Data
@Accessors(chain = true)
public class MenuVo {

    private String name;
    private String path;
    private String icon;
    private String permission;
    private List<MenuVo> children;
}
