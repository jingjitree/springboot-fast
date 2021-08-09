package io.renren.cluster.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@TableName(value = "pay_order")
public class PayOrder extends BaseEntity<PayOrder> {

    private String orderNo;
    private String chOrderNo;
    private String preChOrderNo;
    private String mchOrderNo;
    private String cashier;
    private String merchantNo;
    private String body;
    private String subject;
    private BigDecimal payAmount;
    private BigDecimal discountAmount;
    private BigDecimal orderRate;
    private BigDecimal channelRate;
    private BigDecimal feeAmount;
    private BigDecimal channelFeeAmount;
    private String channelNo;
    private Integer payType;
    private Integer payCategory;
    private String appid;
    private String openid;
    private String subOpenid;
    private String authCode;
    private Integer orderStatus;
    private String orderDesc;
    private String notifyUrl;
    private BigDecimal allRefundAmount;
    private Date payTime;
    private String reqIp;
    private String remark;

}
