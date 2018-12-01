package com.mk.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig{

  @Value("${example.property}")
  private String exampleProperty;
  
  @Value("${license.update}")
  private String licenseUpdate;
  
  @Value("${license.save}")
  private String licenseSave;
  
  @Value("${license.delete}")
  private String deleteLicense;
  
  public String getExampleProperty(){
    return exampleProperty;
  }
  public String getlicenseUpdate(){
	    return licenseUpdate;
	  }
  public String getlicenseSave(){
	    return licenseSave;
	  }
  public String getdeleteLicense(){
	    return deleteLicense;
	  }
}

