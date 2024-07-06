package com.kelempok7.serverapp.models.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private String error;

    public GenericResponse (String message){
        super();
        this.message = message;
    }
    
    public GenericResponse (String message, String error){
        super();
        this.message = message;
        this.error = error;
    }
}
