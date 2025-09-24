package com.example.animal_shelet.TypeHandler.Animal.AnimalProfile;

import com.example.animal_shelet.pojo.Animal.AnimalProfile;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AnimalProfile.AnimalStatus.class)
public class AnimalStatusTypeHandler extends BaseTypeHandler<AnimalProfile.AnimalStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AnimalProfile.AnimalStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public AnimalProfile.AnimalStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return AnimalProfile.AnimalStatus.fromCode(code);
    }

    @Override
    public AnimalProfile.AnimalStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return AnimalProfile.AnimalStatus.fromCode(code);
    }

    @Override
    public AnimalProfile.AnimalStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return AnimalProfile.AnimalStatus.fromCode(code);
    }
}
