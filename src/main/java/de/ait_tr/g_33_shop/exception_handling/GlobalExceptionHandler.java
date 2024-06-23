package de.ait_tr.g_33_shop.exception_handling;

import de.ait_tr.g_33_shop.exception_handling.exceptions.FourTestException;
import de.ait_tr.g_33_shop.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ThirdTestException.class)
   public ResponseEntity<Response> handleException(ThirdTestException e){
       Response response = new Response(e.getMessage());
       return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

   }

    @ExceptionHandler(FourTestException.class)
    public ResponseEntity<Response> handleException(FourTestException e){
        Response response = new Response(e.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
