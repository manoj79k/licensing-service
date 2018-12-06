package com.mk.licenses.controllers;

import com.mk.licenses.config.ServiceConfig;
import com.mk.licenses.model.License;
import com.mk.licenses.services.LicenseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value="v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    @Autowired
    private LicenseService licenseService;
    
    @Autowired
    ServiceConfig serviceConfig;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<License> getLicenses( @PathVariable("organizationId") String organizationId) {
    	System.out.println("==========="+serviceConfig.getExampleProperty());
       return  licenseService.getLicenses();
       
    }
    
    @RequestMapping(value="client/{licenseId}/{clientType}",method = RequestMethod.GET)
    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId,
                                          @PathVariable("clientType") String clientType) {
    		return licenseService.getLicense(organizationId,licenseId, clientType);	
    }
   
    @RequestMapping(value="withpoutclient/{licenseId}",method = RequestMethod.GET)
    public License getLicensesWithpoutClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId) {
    
    	System.out.println("getLicensesWithpoutClient=====");	
    	return licenseService.getLicense(organizationId,licenseId);
    }

    
    @RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
    public License getLicense( @PathVariable("organizationId") String organizationId,
                                @PathVariable("licenseId") String licenseId) {

       return licenseService.getLicense(licenseId);
       
    }
   

    @RequestMapping(value="{licenseId}",method = RequestMethod.PUT)
    public String updateLicenses( @PathVariable("licenseId") String licenseId) {
        License license=new License();
        license.setLicenseId(licenseId);
        license.setProductName("updated aims 2");
    	
    	return licenseService.updateLicense(license);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveLicenses(@RequestBody License licnese) {
         licenseService.saveLicense(licnese);
         return serviceConfig.getlicenseSave();
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses( @PathVariable("licenseId") String licenseId) {
    	 licenseService.deleteLicense(licenseId);
    	return serviceConfig.getdeleteLicense();
    }
}
