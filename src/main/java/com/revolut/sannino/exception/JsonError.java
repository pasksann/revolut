package com.revolut.sannino.exception;

public class JsonError extends RuntimeException{
  private String type;
  private String message;
  
  public JsonError(String type, String message){
    this.type = type;
    this.message = message;        
  }
  
  public String getType(){
    return this.type;
  }
  
  public String getMessage(){
    return this.message;
  }
}