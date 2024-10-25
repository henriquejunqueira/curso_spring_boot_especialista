package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.exception.RegraNegocioException;
import io.github.henriquejunqueira.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice // contém o @ControllerAdvice, @ResponseBody e outras anotações
// @ControllerAdvice // trata erros de todos os controllers de uma vez
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    // @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

}
