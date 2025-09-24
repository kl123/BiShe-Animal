package com.example.animal_shelet.TypeHandler.Animal.AuditRecordsTypeHandler;

import com.example.animal_shelet.pojo.Animal.AuditRecords;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AuditRecords.Target.class)
public class TargetTypeTypeHandler extends BaseTypeHandler<AuditRecords.Target> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AuditRecords.Target parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public AuditRecords.Target getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : AuditRecords.Target.fromValue(value);
    }

    @Override
    public AuditRecords.Target getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : AuditRecords.Target.fromValue(value);
    }

    @Override
    public AuditRecords.Target getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : AuditRecords.Target.fromValue(value);
    }
}
