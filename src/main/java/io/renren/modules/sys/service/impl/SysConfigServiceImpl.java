/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.renren.common.exception.RRException;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.entity.vo.ConfigListVo;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Override
	public IPage<SysConfigEntity> queryPage(ConfigListVo vo) {
		String paramKey = vo.getParamKey();

		Page<SysConfigEntity> pageParam = new Page<>(vo.getPage(), vo.getPageSize());

		return this.page(pageParam,
			new QueryWrapper<SysConfigEntity>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
				.eq("status", 1)
		);
	}

	@Override
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = baseMapper.queryByKey(key);
		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
