/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author HP
 */
@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{
    
    
    @Override
    public Response toResponse(InvalidInputException exception){
        
        
        Response res = Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \""+exception.getMessage()+"\"}")
                .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
}
