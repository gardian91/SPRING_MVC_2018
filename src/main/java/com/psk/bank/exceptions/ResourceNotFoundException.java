package com.psk.bank.exceptions;

public class ResourceNotFoundException extends Exception {
	 
    private String resourceId;
 
    public ResourceNotFoundException(String resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
}