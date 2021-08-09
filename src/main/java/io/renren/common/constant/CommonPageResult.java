package io.renren.common.constant;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分页数据实体")
public class CommonPageResult<T> extends CommonResult<T>{

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录条数")
    private Long totalCount;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Long totalPage;


    public static <T> CommonPageResult<T> success(T data, Long totalCount, Long totalPage){
        CommonPageResult<T> result = new CommonPageResult<>(totalCount, totalPage);
        result.setData(data)
                .setCode(SUCCESS_CODE)
                .setMsg("请求成功");
        return result;
    }

}
