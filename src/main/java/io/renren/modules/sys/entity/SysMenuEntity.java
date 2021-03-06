/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity<SysMenuEntity> {

	@TableField(value = "id")
	private Integer menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Integer parentId;
	
	/**
	 * 父菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private Integer type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;

	@TableField(exist = false)
	private List<?> list;

}
