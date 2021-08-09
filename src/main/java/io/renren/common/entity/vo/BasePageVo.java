package io.renren.common.entity.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePageVo implements java.io.Serializable{

    private Integer page = 1;

    private Integer pageSize = 10;
}
