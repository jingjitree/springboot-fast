/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import cn.hutool.core.map.MapUtil;
import io.renren.common.constant.CommonResult;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * APP测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class AppTestController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public CommonResult<Map<String, Object>> userInfo(@LoginUser UserEntity user){
        return CommonResult.success(MapUtil.of("user", user));
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public CommonResult<Map<String, Object>> userInfo(@RequestAttribute("userId") Integer userId){
        return CommonResult.success(MapUtil.of("userId", userId));
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public CommonResult<Map<String, Object>> notToken(){
        return CommonResult.success(MapUtil.of("msg", "无需token也能访问。。。"));
    }

}
