package com.example.animal_shelet.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义TypeHandler，用于处理数据库中字符串格式的列表与Java List<String>之间的转换
 * 数据库格式：[url1, url2, url3] 或 [url1]
 * Java格式：List<String>
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 将List<String>转换为数据库存储格式的字符串
        if (parameter == null || parameter.isEmpty()) {
            ps.setString(i, "[]");
        } else {
            ps.setString(i, parameter.toString());
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return parseStringToList(value);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return parseStringToList(value);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return parseStringToList(value);
    }

    /**
     * 解析数据库中的字符串格式为List<String>
     * 支持格式：[url1, url2, url3] 或 [url1] 或 []
     */
    private List<String> parseStringToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // 去除首尾的方括号
        value = value.trim();
        if (value.startsWith("[") && value.endsWith("]")) {
            value = value.substring(1, value.length() - 1);
        }
        
        // 如果内容为空，返回空列表
        if (value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // 按逗号分割并去除每个元素的空格
        String[] items = value.split(",");
        List<String> result = new ArrayList<>();
        for (String item : items) {
            String trimmedItem = item.trim();
            if (!trimmedItem.isEmpty()) {
                result.add(trimmedItem);
            }
        }
        
        return result;
    }
}