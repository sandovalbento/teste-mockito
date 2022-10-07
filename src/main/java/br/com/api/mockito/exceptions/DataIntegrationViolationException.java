package br.com.api.mockito.exceptions;

public class DataIntegrationViolationException extends  RuntimeException{

    public DataIntegrationViolationException(String message){
        super(message);
    }
}
