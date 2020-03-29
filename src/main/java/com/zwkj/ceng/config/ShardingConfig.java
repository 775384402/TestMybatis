package com.zwkj.ceng.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.Lists;
import com.zwkj.ceng.apollo.bean.DataSourceConfigBean;
import com.zwkj.ceng.mybatis.intecepter.MyIntercepter;
import com.zwkj.ceng.sharding.MyPreciseShardingAlgorithm;
import com.zwkj.ceng.sharding.MyRangeShardingAlgorithm;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Created by zwhd on 2020/3/25
 */
@Configuration
public class ShardingConfig {

	@Autowired
	private DataSourceConfigBean dataSourceConfigBean;

	private DruidDataSource createDataSource(String url, String username, String password, String driverClassName,
			Integer initialSize, Integer minIdle, Integer maxActive, Integer maxWait) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("select 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		dataSource.setLogAbandoned(true);
		dataSource.setConnectionProperties("config.decrypt=false");
		dataSource.setBreakAfterAcquireFailure(true);
		Properties connectProperties = new Properties();
		connectProperties.setProperty("druid.stat.slowSqlMillis", "5000");
		connectProperties.setProperty("druid.stat.logSlowSql", "true");
		connectProperties.setProperty("druid.log.rs", "false");
		dataSource.setConnectProperties(connectProperties);
		return dataSource;
	}

	@Bean
	DataSource getDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(
				"category_id", new MyPreciseShardingAlgorithm(), new MyRangeShardingAlgorithm()));
		shardingRuleConfig.setDefaultTableShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("sale_prop", "lazada_category_attribute_${sale_prop % 2}"));
		shardingRuleConfig.setDefaultTableShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("category_id", "lazada_category_attribute_${sale_prop % 2}"));
		shardingRuleConfig.setDefaultDataSourceName("test_01");
		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
	}

	private DataSource dataSource01() {
		// 配置第一个数据源
		return createDataSource(dataSourceConfigBean.getUrl(),dataSourceConfigBean.getUsername(),
				dataSourceConfigBean.getPassword(), dataSourceConfigBean.getDriverClassName(),
				dataSourceConfigBean.getInitialSize(), dataSourceConfigBean.getMinIdle(),
				dataSourceConfigBean.getMaxActive(), dataSourceConfigBean.getMaxWait());
	}

	private DataSource dataSource02() {
		// 配置第二个数据源
		return createDataSource("jdbc:mysql://122.51.239.204:3306/test_02?useSSL=false", "cceng", "c775384402",
				dataSourceConfigBean.getDriverClassName(), dataSourceConfigBean.getInitialSize(),
				dataSourceConfigBean.getMinIdle(), dataSourceConfigBean.getMaxActive(),
				dataSourceConfigBean.getMaxWait());
	}

	private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
		MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("test_01", "test_01",
				Arrays.asList("test_02"));
		return Lists.newArrayList(masterSlaveRuleConfig);
	}

	private Map<String, DataSource> createDataSourceMap() {
		final Map<String, DataSource> result = new HashMap<>();
		result.put("test_01", dataSource01());
		result.put("test_02", dataSource02());
		return result;
	}

	private TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration("lazada_category_attribute",
				"test_0${1..2}.lazada_category_attribute_${[0, 1]}");
		return result;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(getDataSource());
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/**/*.xml"));
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageHelperInterceptor() });
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { new MyIntercepter() });
		return sqlSessionFactoryBean;
	}

	@Bean
	public static MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.zwkj.ceng.mybatis.mapper");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		// mybatis 自实现的通用接口
//        mapperScannerConfigurer.getMapperHelper().registerMapper(SelectLimitMapper.class);
		return mapperScannerConfigurer;
	}

	private static PageInterceptor pageHelperInterceptor() {
		// 设置分页的拦截器
		PageInterceptor pageInterceptor = new PageInterceptor();
		// 创建插件需要的参数集合
		pageInterceptor.setProperties(properties());
		return pageInterceptor;
	}

	private static Properties properties() {
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

}
