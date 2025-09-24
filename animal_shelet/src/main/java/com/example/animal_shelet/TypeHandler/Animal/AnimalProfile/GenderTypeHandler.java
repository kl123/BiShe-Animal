package com.example.animal_shelet.TypeHandler.Animal.AnimalProfile;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AnimalProfile.Gender.class)
public class GenderTypeHandler extends BaseTypeHandler<AnimalProfile.Gender> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AnimalProfile.Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public AnimalProfile.Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return AnimalProfile.Gender.fromCode(code);
    }

    @Override
    public AnimalProfile.Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return AnimalProfile.Gender.fromCode(code);
    }

    @Override
    public AnimalProfile.Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return AnimalProfile.Gender.fromCode(code);
    }
}
