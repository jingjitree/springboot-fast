package io.renren.common.constant;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class DataConstant {

    @Value("${mybatisPlus.configLocation}")
    private String configLocation;

    @Value("${mybatisPlus.master.mapperLocations}")
    private String masterMapperLocations;
    @Value("${mybatisPlus.master.typeAliasesPackage}")
    private String masterTypeAliasesPackage;

    @Value("${mybatisPlus.cluster.mapperLocations}")
    private String clusterMapperLocations;
    @Value("${mybatisPlus.cluster.typeAliasesPackage}")
    private String clusterTypeAliasesPackage;




}
