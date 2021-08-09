package io.renren.common.constant;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "响应结果")
public class CommonResult<T> {
    protected static final Integer SUCCESS_CODE = 1;

    @ApiModelProperty(value = "接口请求状态码：1.成功")
    private Integer code;

    @ApiModelProperty(value = "接口响应信息")
    private String msg;

    private T data;

    public static <T> CommonResult<T> success(){
        return new CommonResult<>(SUCCESS_CODE, "success", null);
    }

    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(SUCCESS_CODE, "success", data);
    }

    public static <T> CommonResult<T> success(String msg){
        return new CommonResult<>(SUCCESS_CODE, msg, null);
    }

    public static <T> CommonResult<T> success(String msg, T data){
        return new CommonResult<>(SUCCESS_CODE, msg, data);
    }

    public static <T> CommonResult<T> fail(){
        return new CommonResult<>(HttpStatus.SC_BAD_REQUEST, "success", null);
    }

    public static <T> CommonResult<T> fail(String msg){
        return new CommonResult<>(HttpStatus.SC_BAD_REQUEST, msg, null);
    }

    public static <T> CommonResult<T> fail(Integer code, String msg){
        return new CommonResult<>(code, msg, null);
    }

}
