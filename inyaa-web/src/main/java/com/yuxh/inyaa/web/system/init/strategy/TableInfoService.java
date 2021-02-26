package com.yuxh.inyaa.web.system.init.strategy;

/**
 * @author zhangshuguang
 * @date 2019/12/25
 */
public interface TableInfoService {

    /**
     * 生成表的DDL语句
     */
    void builderTable(java.sql.Statement stat);

}
