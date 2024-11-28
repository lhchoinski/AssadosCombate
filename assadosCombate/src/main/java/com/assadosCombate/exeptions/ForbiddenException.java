package com.assadosCombate.exeptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Não autorizado");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
