package com.inyaa.web.auth.vo;

import com.inyaa.web.auth.bean.SysDept;
import lombok.Data;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/27 18:53
 */
@Data
public class SysDepotVo extends SysDept {

    private List<SysDepotVo> children;
}
