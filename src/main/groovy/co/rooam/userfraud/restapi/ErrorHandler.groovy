package co.rooam.userfraud.restapi

import co.rooam.userfraud.model.VerificationInfo
import co.rooam.userfraud.service.FraudUserDetectedException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FraudUserDetectedException.class)
    ResponseEntity<VerificationInfo> handleFraudUser(FraudUserDetectedException ex) {
        return new ResponseEntity<>(ex.getVerificationInfo(), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @Override
    protected ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
                                                          , HttpHeaders headers
                                                          , HttpStatus status
                                                          , WebRequest request) {
        return new ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
    }
}
