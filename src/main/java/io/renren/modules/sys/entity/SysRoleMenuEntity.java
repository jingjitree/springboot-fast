/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role_menu")
public class SysRoleMenuEntity extends BaseEntity<SysRoleMenuEntity> {

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 菜单ID
	 */
	private Integer menuId;
	
}
