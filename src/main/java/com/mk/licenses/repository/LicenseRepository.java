package com.mk.licenses.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mk.licenses.config.ServiceConfig;
import com.mk.licenses.model.License;

@Repository
public class LicenseRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
    ServiceConfig serviceConfig;
	

	
	
	public License findByOrganizationIdAndLicenseId(String organizationId,String licenseId )
	{
		System.out.println("organizationId: "+organizationId+"   licenseId:"+licenseId);
		String licenseSQL="select ID,ORGANIZATIONID,PRODUCTNAME,LICENSETYPE from license where id=? and ORGANIZATIONID=?";
		License license = (License)jdbcTemplate.queryForObject(licenseSQL,new Object[] {licenseId,organizationId}, new LicenseRowMapper());
		return license;
		
	}
	
	public License getLicense(String licenseId )
	{
		
		String licenseSQL="select ID,ORGANIZATIONID,PRODUCTNAME,LICENSETYPE from license where id=?";
		License license = (License)jdbcTemplate.queryForObject(licenseSQL,new Object[] {licenseId}, new LicenseRowMapper());
		return license;
		
	}
	public List<License> getLicenses()
	{
		
		String licenseSQL="select ID,ORGANIZATIONID,PRODUCTNAME,LICENSETYPE from license";
		List<License> license  = jdbcTemplate.query(licenseSQL,
				new BeanPropertyRowMapper(License.class));
		return license;
	}
	
	public String updateLicenses(License license)
	{
		String updateSQL="update license set PRODUCTNAME=? where id=? ";
		int count=jdbcTemplate.update(updateSQL, new Object[] {license.getProductName(),license.getLicenseId()});
		System.out.println("update count=====" +count);
		return  serviceConfig.getlicenseUpdate(); 
	}
	public void saveLicense(License license)
	{
		String insertSQL="Insert into license(ID,ORGANIZATIONID,PRODUCTNAME,LICENSETYPE) values(?,?,?,?)";
		int count=jdbcTemplate.update(insertSQL, new Object[] {license.getLicenseId(),license.getOrganizationId(),license.getProductName(),license.getLicenseType()});
		System.out.println("insert count=====" +count);
		 
	}
	
	public void deleteLicenses(String licenseId)
	{
		String deleteSQL="delete from license where id=? ";
		int count=jdbcTemplate.update(deleteSQL, new Object[] {licenseId});
		System.out.println("delete count=====" +count);
		
	}
	
}
