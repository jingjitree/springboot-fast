/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;


import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import io.renren.common.exception.RRException;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.sys.service.SysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        redisUtils.setValueTimeout(uuid, code, 5, TimeUnit.MINUTES);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        if (StrUtil.isBlank(code) || StrUtil.isBlank(uuid))
            return false;
        String uuidCode = (String) redisUtils.getValue(uuid);
        //删除缓存
        redisUtils.delKey(uuid);
        if (code.equals(uuidCode))
            return true;
        return false;
    }
}
