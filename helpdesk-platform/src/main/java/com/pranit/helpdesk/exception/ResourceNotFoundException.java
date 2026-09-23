package com.pranit.helpdesk.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resource, Object identifier) {
    super(resource + " with identifier " + identifier + " was not found");
  }
}
