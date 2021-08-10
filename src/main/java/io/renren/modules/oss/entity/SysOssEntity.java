/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;


/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_oss")
public class SysOssEntity extends BaseEntity<SysOssEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//URL地址
	private String url;

}
