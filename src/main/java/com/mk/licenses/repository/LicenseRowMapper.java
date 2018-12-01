package com.mk.licenses.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mk.licenses.model.License;

public class LicenseRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		License license = new License();
		license.setLicenseId(rs.getString("ID"));
		license.setOrganizationId(rs.getString("ORGANIZATIONID"));
		license.setProductName(rs.getString("PRODUCTNAME"));
		license.setLicenseType(rs.getString("LICENSETYPE"));
		return license;
	}

}