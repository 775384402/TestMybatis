package com.zwkj.ceng.config;

import static org.apache.ibatis.session.LocalCacheScope.STATEMENT;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInterceptor;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@EnableTransactionManagement
public class MyBatisSpringConfig {

        private final String url = "jdbc:mysql://122.51.239.204:3306/test_01?useSSL=false";
//    private final String url = "jdbc:mysql://192.168.2.11:3306/zwdc_j?useSSL=false";
    private final String driverName = "com.mysql.jdbc.Driver";
    private final String username = "cceng";
    private final String password = "c775384402";
    private final int maxActive = 4;
    private final int initialSize = 1;
    private final int maxWait = 6000;
    private final int minIdle = 1;
    private final int timeBetweenEvictionRunsMillis = 60000;
    private final int minEvictableIdleTimeMillis = 300000;
    private final int maxOpenPreparedStatements = 3;
    private final boolean poolPreparedStatements = true;

    @DeleteMapping
    @Bean(value = "dataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        dataSource.setInitialSize(initialSize);
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(maxWait);
        // 最大连接池数量
        dataSource.setMaxActive(maxActive);
        // 最小空闲连接
        dataSource.setMinIdle(minIdle);
        // 每 timeBetweenEvictionRunsMillis 秒运行一次空闲连接回收器
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 池中的连接空闲30分钟后被回收,默认值就是30分钟。
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        return dataSource;
    }

    @Bean(value = "sqlSessionFactory")
    public SqlSessionFactory loadSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置 TransactionFactory
        sqlSessionFactoryBean.setTransactionFactory(new JdbcTransactionFactory());
        // 设置 DataSource
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 扫描 mapper.xml文件路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        // 设置分页插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelperInterceptor()});
        // set SqlSessionFactoryBuilder
        sqlSessionFactoryBean.setSqlSessionFactoryBuilder(new SqlSessionFactorySettingBuilder());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    public static PageInterceptor pageHelperInterceptor() {
        // 设置分页的拦截器
        PageInterceptor pageInterceptor = new PageInterceptor();
        // 创建插件需要的参数集合
        Properties properties = properties();
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    public static Properties properties() {
        Properties properties = new Properties();
        properties.put("offsetAsPageNum", "true");
        properties.put("rowBoundsWithCount", "true");
        properties.put("pageSizeZero", "true");
        properties.put("reasonable", "false");
        properties.put("params", "pageNum=pageHelperStart;pageSize=pageHelperRows;");
        properties.put("supportMethodsArguments", "false");
        properties.put("returnPageInfo", "none");

        return properties;
    }

    // 自定义 SqlSessionFactoryBuilder 修改 Configuration
    class SqlSessionFactorySettingBuilder extends SqlSessionFactoryBuilder {
        @Override
        public SqlSessionFactory build(org.apache.ibatis.session.Configuration config) {

            loadConfiguration(config);
            return new DefaultSqlSessionFactory(config);
        }
        // 设置 configuration
        public org.apache.ibatis.session.Configuration loadConfiguration(
                org.apache.ibatis.session.Configuration configuration) {
            // MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。
            // 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。
            // 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。
            configuration.setLocalCacheScope(STATEMENT);
            // 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，
            // 这在依赖于 Map.keySet() 或 null 值初始化的时候比较有用。
            // 注意基本类型（int、boolean 等）是不能设置成 null 的。
            configuration.setCallSettersOnNulls(true);
            // 在此配置下，全局启用或禁用在任何映射器中配置的所有缓存。
            configuration.setCacheEnabled(true);
            // 全局启用或禁用延迟加载。启用后，所有关系都会被延迟加载。可以通过使用fetchType属性将其替换为特定关系。
            configuration.setLazyLoadingEnabled(false);
            // 启用后，任何方法调用都将加载对象的所有惰性属性。否则，将按需加载每个属性 ≤3.4.1为真
            configuration.setAggressiveLazyLoading(true);
            // 允许或禁止从单个语句返回多个ResultSet（需要兼容的驱动程序）
            configuration.setMultipleResultSetsEnabled(true);
            // 指定 MyBatis 增加到日志名称的前缀。
            configuration.setLogPrefix("-Cceng-");

            configuration.setMapUnderscoreToCamelCase(true);
            return configuration;
        }

    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }

//    /**
//     * 注入sqlSession对象
//     *
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean(value = "sqlSession")
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(loadSqlSessionFactory());
//    }

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
