package com.example.animal_shelet.TypeHandler.Adopte.AdoptionApplications;

import com.example.animal_shelet.pojo.Adopte.AdoptionApplications;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AdoptionApplications.AdoptionApplicationsStatus.class)
public class AdoptionApplicationsStatusTypeHandler extends BaseTypeHandler<AdoptionApplications.AdoptionApplicationsStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AdoptionApplications.AdoptionApplicationsStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public AdoptionApplications.AdoptionApplicationsStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return AdoptionApplications.AdoptionApplicationsStatus.fromCode(code);
    }

    @Override
    public AdoptionApplications.AdoptionApplicationsStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return AdoptionApplications.AdoptionApplicationsStatus.fromCode(code);
    }

    @Override
    public AdoptionApplications.AdoptionApplicationsStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return AdoptionApplications.AdoptionApplicationsStatus.fromCode(code);
    }
}
