/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysRoleMenuDao;
import io.renren.modules.sys.entity.SysRoleMenuEntity;
import io.renren.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
		//先删除角色与菜单关系
		this.deleteBatch(new Integer[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}
		List<SysRoleMenuEntity> roleMenus = menuIdList.stream().map(menuId ->
				new SysRoleMenuEntity()
						.setMenuId(menuId)
						.setRoleId(roleId)).collect(Collectors.toList());
		//保存角色与菜单关系
		saveBatch(roleMenus);
	}

	@Override
	public List<Integer> queryMenuIdList(Integer roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Integer[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
