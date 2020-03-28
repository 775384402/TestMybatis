package com.zwkj.ceng.mybatis.intecepter;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zwhd on 2020/3/23
 */
@Intercepts(
        {
                @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        }
)
public class MyIntercepter implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Statement stmt = (Statement) args[0];
        ResultSet resultSet = stmt.getResultSet();
        try {
            while (resultSet.next()) {
                try {
                    resultSet.getString(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
