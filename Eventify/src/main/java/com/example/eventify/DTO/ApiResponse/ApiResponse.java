package com.example.eventify.DTO.ApiResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class ApiResponse<T> {
    private T ResponseData;
    private List<String> Errors;

    public ApiResponse(){
    }

    public ApiResponse(T data) {
        ResponseData = data;
    }

    public ApiResponse(T data, List<String> errors) {
        ResponseData = data;
        Errors = errors;
    }

    public ApiResponse(List<String> errors) {
        Errors = errors;
    }

    public ApiResponse(String error){
        Errors = new ArrayList<>(Collections.singletonList(error));
    }
}
