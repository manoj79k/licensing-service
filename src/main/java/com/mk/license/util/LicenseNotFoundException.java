package com.mk.license.util;


public class LicenseNotFoundException extends RuntimeException {

	private final String id;

	  public LicenseNotFoundException(String id) {
	    super("License could not be found with id: " + id);
	    this.id = id;
	  }

	public String getId() {
		return id;
	}
	
	  
}
