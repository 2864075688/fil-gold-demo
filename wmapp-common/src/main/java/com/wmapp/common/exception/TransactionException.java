package com.wmapp.common.exception;

/**
 * @author wmapp
 * @date 2021/11/1 15:58
 */
public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
