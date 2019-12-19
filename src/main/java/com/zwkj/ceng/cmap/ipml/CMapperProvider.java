package com.zwkj.ceng.cmap.ipml;

import com.zwkj.ceng.entity.Commodity;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class CMapperProvider  {

    public String cSelectByComForUpdate(Object object) {
        String tableName = object.getClass().getSimpleName();
        StringBuilder sql = new StringBuilder();
        sql.append("Select *");
        sql.append(" From " + tableName.toLowerCase());
        sql.append(" Where id=#{id} ");
        sql.append(" for update");
        return sql.toString();
    }

    public String cSelectAll(Object object) {
        String tableName = object.getClass().getSimpleName();
        StringBuilder sql = new StringBuilder();
        sql.append("Select *");
        sql.append(" From " + tableName.toLowerCase());
        return sql.toString();
    }

}
