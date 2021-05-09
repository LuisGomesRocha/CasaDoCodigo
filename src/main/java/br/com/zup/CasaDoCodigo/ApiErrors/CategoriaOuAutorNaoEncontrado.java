package br.com.zup.CasaDoCodigo.ApiErrors;

public class CategoriaOuAutorNaoEncontrado extends RuntimeException{
    public CategoriaOuAutorNaoEncontrado(String message) {
        super(message);
    }
}
