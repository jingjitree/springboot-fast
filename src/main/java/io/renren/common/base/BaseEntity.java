package io.renren.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BaseEntity<T extends BaseEntity<T>> extends Model<T> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createTime;
    private Date updateTime;

}
