package com.zwkj.ceng.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MyBatisSpringConfig implements TransactionManagementConfigurer {
    @Autowired
    private DataSource dataSource;

    // 在Spring中注册SqlSessionFactory，在这里可以设置一下参数：
    // 1.设置分页参数
    // 2.配置MyBatis运行时参数
    // 3.注册xml映射器
    @Bean
    public SqlSessionFactory sqlSessionFactory() {

        String resource = "mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Properties properties = new Properties();
        properties.setProperty("username", "root");
        properties.setProperty("password", "C775384402");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //  在此配置下，全局启用或禁用在任何映射器中配置的所有缓存。
        configuration.setCacheEnabled(false);
        // 全局启用或禁用延迟加载。启用后，所有关系都会被延迟加载。可以通过使用fetchType属性将其替换为特定关系。
        configuration.setLazyLoadingEnabled(false);
        // 启用后，任何方法调用都将加载对象的所有惰性属性。否则，将按需加载每个属性 ≤3.4.1为真
        configuration.setAggressiveLazyLoading(true);
        // 允许或禁止从单个语句返回多个ResultSet（需要兼容的驱动程序）
        configuration.setMultipleResultSetsEnabled(true);


        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(reader,properties);

        return sqlSessionFactory;


    }

    /**
     * 注入sqlSession对象
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(value = "sqlSession")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // Spring事务管理器
    @Bean(value = "transactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
