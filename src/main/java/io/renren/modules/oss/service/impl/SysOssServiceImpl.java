/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.oss.dao.SysOssDao;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.entity.vo.OssListVo;
import io.renren.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;


@Service
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public IPage<SysOssEntity> queryPage(OssListVo vo) {
		Page<SysOssEntity> pageParam = new Page<>(vo.getPage(), vo.getPageSize());

		return page(pageParam);
	}
	
}
