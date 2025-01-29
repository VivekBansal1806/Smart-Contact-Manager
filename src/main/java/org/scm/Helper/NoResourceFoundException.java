package org.scm.Helper;

public class NoResourceFoundException extends RuntimeException{

    public NoResourceFoundException(String message){
        super(message);
    }

    public NoResourceFoundException(){
        super("Resource Not Found");
    }
}
