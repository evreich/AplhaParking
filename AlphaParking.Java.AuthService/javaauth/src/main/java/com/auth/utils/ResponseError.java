package com.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseError {
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper(); // HACK!!!!!

    private ObjectNode generateJSONResponse(String message){
        ObjectNode jsonObject = objectMapper.createObjectNode();
        return jsonObject.put("error", message);
    }

    public ResponseEntity<ObjectNode> generateResponse(String message, HttpStatus status) {
        return new ResponseEntity<ObjectNode>(
            this.generateJSONResponse(message), 
            status
        );
    }
}