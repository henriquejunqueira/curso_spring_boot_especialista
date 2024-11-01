package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.exception.PedidoNaoEncontradoException;
import io.github.henriquejunqueira.exception.RegraNegocioException;
import io.github.henriquejunqueira.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(erro -> erro.getDefaultMessage())
            .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

}
