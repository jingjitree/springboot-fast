package io.renren.common.constant;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "renren.jwt")
public class JwtConstant {

    private String secret;
    private Long expire;
    private String header;
    private String codeKey;
    private String userTokenKey;

}
