package br.com.zup.CasaDoCodigo.ApiErrors;

public class CategoriaNaoEcontradaException extends RuntimeException {
    public CategoriaNaoEcontradaException(String msg) {
        super(msg);
    }
}
