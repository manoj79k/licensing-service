package com.mk.licenses.services;

import com.mk.licenses.clients.OrganizationDiscoveryClient;
import com.mk.licenses.clients.OrganizationFeignClient;
import com.mk.licenses.clients.OrganizationRestTemplateClient;
import com.mk.licenses.config.ServiceConfig;
import com.mk.licenses.model.License;
import com.mk.licenses.model.Organization;
import com.mk.licenses.repository.LicenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

	@Autowired
	LicenseRepository licenseRepository;
	
	
	@Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
    
	@Autowired ServiceConfig config;
    
    private Organization retrieveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    
    
	
    public License getLicense(String organizationId, String licenseId, String clientType){
        
    	//License license =licenseRepository.getLicense(licenseId);
    	License license =licenseRepository.findByOrganizationIdAndLicenseId(organizationId,licenseId);
    	Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
        
    }
    
 public License getLicense(String licenseId){
        
    	 return licenseRepository.getLicense(licenseId);
    	
    }
 
   
    public List<License> getLicenses(){
        return licenseRepository.getLicenses();
    }

    public void saveLicense(License license){
    	 licenseRepository.saveLicense(license);
    }

    public String updateLicense(License license){
    	return licenseRepository.updateLicenses(license);
    }

    public void deleteLicense(String licenseId){
    	 licenseRepository.deleteLicenses(licenseId);
    }

}
