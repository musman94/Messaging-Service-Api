package com.app.exception;

import org.springframework.stereotype.Component;

@Component
public class AppException {
    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String id) {
        String message = getMessage(entityType, id, exceptionType);
        return throwException(exceptionType, message);
    }

    private static RuntimeException throwException(ExceptionType exceptionType, String message) {
        if(ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(message);
        }

        else if(ExceptionType.ENTITY_ALREADY_EXISTS.equals(exceptionType)) {
            return new EntityAlreadyExists(message);
        }

        return new RuntimeException(message);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class EntityAlreadyExists extends RuntimeException {
        public EntityAlreadyExists(String message) {
            super(message);
        }
    }

    private static String getMessage(EntityType entityType, String id, ExceptionType exceptionType) {
         return entityType.name().concat(" ").concat(id).concat(" ").concat(exceptionType.getValue().toLowerCase());
    }

}
