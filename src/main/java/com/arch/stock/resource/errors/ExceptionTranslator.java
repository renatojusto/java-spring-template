package com.arch.stock.resource.errors;

import com.arch.stock.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends Throwable {

    private final transient Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorApplication processValidationError(NotFoundException ex) {
        logError(ex);
        return new ErrorApplication(ErrorConstants.ERR_VALIDATION, ex.getMessage());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorApplication processValidationError(IllegalArgumentException ex) {
        logError(ex);
        return new ErrorApplication(ErrorConstants.ERR_VALIDATION, ex.getMessage());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorVM processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorVM processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorVM(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> processException(Exception ex) {
        logError(ex);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            return ResponseEntity
                .status(responseStatus.value())
                .body(new ErrorVM("error." + responseStatus.value().value(), responseStatus.reason()));
        }

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR,
                              "Erro Desconhecido. Por favor consulte os detalhes no log no servidor."));
    }

    private void logError(Exception ex) {
        if (log.isDebugEnabled()) {
            log.debug("Um erro inesperado ocorreu: {}", ex.getMessage(), ex);
        } else {
            log.error("Um erro inesperado ocorreu: {}", ex.getMessage());
            if (isNull(ex.getMessage()) || ex.getMessage().toLowerCase().contains("null")) {
                log.error("Um erro NULL inesperado ocorreu: {}", "Mensagem Nula, analise o stacktrace", ex);
            }
        }
    }

}
