package com.example.eventify.DTO.ErrorResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private HttpStatus HttpStatus;
    private String Message;
}
