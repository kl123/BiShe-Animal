package com.example.animal_shelet.TypeHandler;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(String.class)
public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        // 将描述文本转换为对应的code
        AnimalProfile.AnimalStatus status = AnimalProfile.AnimalStatus.fromDescription(parameter);
        ps.setInt(i, status.getCode());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) return null;
        int code = rs.getInt(columnName);
        return AnimalProfile.AnimalStatus.fromCode(code).getDescription();
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) return null;
        int code = rs.getInt(columnIndex);
        return AnimalProfile.AnimalStatus.fromCode(code).getDescription();
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) return null;
        int code = cs.getInt(columnIndex);
        return AnimalProfile.AnimalStatus.fromCode(code).getDescription();
    }
}
