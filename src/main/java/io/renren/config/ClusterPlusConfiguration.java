package io.renren.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.renren.common.constant.DataConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "io.renren.cluster.dao",
        sqlSessionTemplateRef = "clusterSqlSessionTemplate")
public class ClusterPlusConfiguration {
    @Autowired
    private DataConstant dataConstant;

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid.cluster")
    public DataSource clusterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory clusterSqlSessionFactory(
            @Qualifier("clusterDataSource") DataSource clusterDataSource,
            @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor) throws Exception{
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(clusterDataSource);
        factoryBean.setConfigLocation(new DefaultResourceLoader().getResource(dataConstant.getConfigLocation()));

        factoryBean.setTypeAliasesPackage(dataConstant.getClusterTypeAliasesPackage());
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(dataConstant.getClusterMapperLocations()));
        factoryBean.setPlugins(paginationInterceptor);
        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager clusterTransactionManager(
            @Qualifier("clusterDataSource") DataSource clusterDataSource){
        return new DataSourceTransactionManager(clusterDataSource);
    }

    @Bean
    public SqlSessionTemplate clusterSqlSessionTemplate(
            @Qualifier("clusterSqlSessionFactory") SqlSessionFactory clusterSqlSessionFactory){
        return new SqlSessionTemplate(clusterSqlSessionFactory);
    }

}
