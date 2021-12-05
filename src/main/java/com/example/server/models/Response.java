package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Used to send a message to the Front-End in case of a request(Bad or Good)
 */
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't show null elements
public class Response {

    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;

}
