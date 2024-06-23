package de.ait_tr.g_33_shop.exception_handling.exceptions;

public class ProductNotFindException extends RuntimeException{
    public ProductNotFindException(String message) {
        super(message);
    }
}
