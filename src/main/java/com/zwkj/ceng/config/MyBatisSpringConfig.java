package com.zwkj.ceng.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zwkj.ceng.mapper.UserMapper;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MyBatisSpringConfig {

    private final String url = "jdbc:mysql://122.51.239.204:3306/test17?useSSL=false";
    private final String driverName = "com.mysql.jdbc.Driver";
    private final String username = "consumer";
    private final String password = "T775384402";
    private final int maxActive = 4;
    private final int initialSize = 1;
    private final int maxWait = 6000;
    private final int minIdle = 1;
    private final int timeBetweenEvictionRunsMillis = 60000;
    private final int minEvictableIdleTimeMillis = 300000;
    private final int maxOpenPreparedStatements = 3;
    private final boolean poolPreparedStatements = true;

    @Bean(value = "dataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        return dataSource;
    }

//    @Bean(value = "sqlSessionFactory")
//    @Order(value = 2)
//    public SqlSessionFactory sqlSessionFactory() {
//        TransactionFactory transactionFactory = new ();
//        Environment environment = new Environment("development", transactionFactory, dataSource());
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
//        //  在此配置下，全局启用或禁用在任何映射器中配置的所有缓存。
////        configuration.setCacheEnabled(false);
//        // 全局启用或禁用延迟加载。启用后，所有关系都会被延迟加载。可以通过使用fetchType属性将其替换为特定关系。
//        configuration.setLazyLoadingEnabled(false);
//        // 启用后，任何方法调用都将加载对象的所有惰性属性。否则，将按需加载每个属性 ≤3.4.1为真
//        configuration.setAggressiveLazyLoading(true);
//        // 允许或禁止从单个语句返回多个ResultSet（需要兼容的驱动程序）
//        configuration.setMultipleResultSetsEnabled(true);
//        // 指定 MyBatis 增加到日志名称的前缀。
//        configuration.setLogPrefix("-Cceng-");
//        configuration.setLogImpl(Log4jImpl.class);
//        configuration.addMapper(UserMapper.class);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        return sqlSessionFactory;
//    }
    @Bean(value = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * 注入sqlSession对象
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(value = "sqlSession")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        // 设置sqlSessionFactory名
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        // 设置接口映射器基础包名
        mapperScannerConfigurer.setBasePackage("com.zwkj.ceng.mapper");
        return mapperScannerConfigurer;
    }

}
