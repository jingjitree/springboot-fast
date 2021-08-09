package io.renren.modules.sys.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.renren.common.entity.vo.BasePageVo;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserListVo extends BasePageVo {

    private String username;

    @JsonIgnore
    private Long createUserId;

}
