package com.devsuperior.demo.controllers.exceptions;

public record FieldMessage(
    String fieldName,
    String message
) {
    
}
