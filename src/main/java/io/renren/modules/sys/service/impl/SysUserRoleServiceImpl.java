/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(MapUtil.of("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return;
		}

		//保存用户与角色关系
		List<SysUserRoleEntity> roleLists = roleIdList.stream().map(roleId -> {
			return new SysUserRoleEntity()
					.setRoleId(roleId)
					.setUserId(userId);
		}).collect(Collectors.toList());
		saveBatch(roleLists);
	}

	@Override
	public List<Integer> queryRoleIdList(Integer userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Integer[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
