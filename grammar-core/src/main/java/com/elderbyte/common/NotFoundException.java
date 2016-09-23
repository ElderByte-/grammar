package com.elderbyte.common;

/**
 * Thrown when an entity was not found.
 */
public class NotFoundException extends RuntimeException {

   public NotFoundException(String message){
       super(message);
   }

}
