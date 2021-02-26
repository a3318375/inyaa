package com.yuxh.inyaa.web.plumemo.menu.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteblogs.common.base.dao.BaseDao;
import com.byteblogs.plumemo.menu.domain.po.Menu;
import com.byteblogs.plumemo.menu.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单表:数据层
 */
public interface MenuDao extends BaseDao<Menu> {
    
    /**
     * 分页查询菜单表
     */
    java.util.List<Menu> selectMenuList(@Param("page") Page<Menu> page, @Param("condition") MenuVO menu);
    java.util.List<Menu> selectMenuList(@Param("condition") MenuVO menu);
}
