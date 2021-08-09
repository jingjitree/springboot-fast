package io.renren.cluster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.cluster.dao.IPayOrderMapper;
import io.renren.cluster.entity.PayOrder;
import io.renren.cluster.service.IPayOrderService;
import org.springframework.stereotype.Service;


@Service
public class PayOrderServiceImpl extends ServiceImpl<IPayOrderMapper, PayOrder> implements IPayOrderService {


}
