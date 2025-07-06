package com.pranit.helpdesk.HelpDesk.ExceptionHandler;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
