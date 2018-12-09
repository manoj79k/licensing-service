package com.mk.licenses.services;

import com.mk.license.util.LicenseNotFoundException;
import com.mk.licenses.clients.OrganizationDiscoveryClient;
import com.mk.licenses.clients.OrganizationFeignClient;
import com.mk.licenses.clients.OrganizationRestTemplateClient;
import com.mk.licenses.config.ServiceConfig;
import com.mk.licenses.model.License;
import com.mk.licenses.model.Organization;
import com.mk.licenses.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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

   
    @HystrixCommand(fallbackMethod="getDefauletOrganization", 
      		 commandProperties= {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1100")}
      		 )
    public Organization getOrganization(String organizationId){
    	System.out.println("License Service getOrganization");
    	randomlyRunLong();
    	randomlyRunLong();
    	return organizationRestClient.getOrganization(organizationId);
    }
    
    public Organization getDefauletOrganization(String organizationId)
   	{
   	 System.out.println("getDefauletOrganization");
      Organization defaultOrganization=new Organization();
   	 defaultOrganization.setContactEmail("om@gmail.com");
   	 defaultOrganization.setContactName("Om Prakasah");
   	 defaultOrganization.setContactPhone("98989328932");
   	 defaultOrganization.setId(organizationId);
   	 defaultOrganization.setName("defalut org name :AIB");
   	 return defaultOrganization;
   		
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
     public License getLicense(String organizationId, String licenseId){
        
    	//License license =licenseRepository.getLicense(licenseId);
    	System.out.println("getLicense service");
    	 Organization org = getOrganization(organizationId);
    	License license =licenseRepository.findByOrganizationIdAndLicenseId(organizationId,licenseId);
    	

        return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
        
    }
 
   
/*    
 @HystrixCommand(fallbackMethod="getDefauletLicense", 
		 commandProperties= {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1000")},
		 threadPoolKey="licenseThreadPool",
		 threadPoolProperties= {@HystrixProperty(name="coreSize",value="7"),
				 			   @HystrixProperty(name="maxQueueSize",value="10")}
		 )*/
 public License getLicense(String licenseId) throws LicenseNotFoundException{
        
	 License license=null;
    try
    {
	 System.out.println("befor call"); 
    // randomlyRunLong();
	 System.out.println("afer call");
	 
	 license= licenseRepository.getLicense(licenseId);
    }
    catch(Exception e)
	    {
	    	throw new LicenseNotFoundException(licenseId);
	    }
    return license;
}
 
 
 
/**
 * This method is fallback method and call when 
 * @param licenseId
 * @return
 */
 public License getDefauletLicense(String licenseId )
	{
		License license=new License();
		license.setLicenseId("666");
		license.setOrganizationId("777");
		license.setProductName("BAAN");
		license.setLicenseType("One Year");
		return license;
		
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

    public void deleteLicense(String licenseId) throws LicenseNotFoundException{
    	try
    	{
    		licenseRepository.deleteLicenses(licenseId);
    		
    	}catch(Exception e)
	    {
	    	throw new LicenseNotFoundException(licenseId);
	    }
    }

    private void randomlyRunLong(){
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum==3) sleep();
      }

      private void sleep(){
          try {
              Thread.sleep(12000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      } 
}
