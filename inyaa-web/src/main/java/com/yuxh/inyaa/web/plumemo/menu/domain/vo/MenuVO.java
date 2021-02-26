package com.yuxh.inyaa.web.plumemo.menu.domain.vo;

import com.byteblogs.common.base.domain.vo.BaseVO;
import com.byteblogs.plumemo.menu.domain.po.Menu;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class MenuVO extends BaseVO<MenuVO> {

	private static final long serialVersionUID = 1L;

    // columns START
	private Long id; 

	/**
	 * 父菜单Id
	 */
	private Long parentId;

	/**
	 * 名称
	 */
	private String title; 

	/**
	 * icon图标
	 */
	private String icon; 

	/**
	 * 跳转路径
	 */
	private String url; 

	/**
	 * 排序
	 */
	private Integer sort;

	private java.util.List<Menu> child;
	// columns END
}