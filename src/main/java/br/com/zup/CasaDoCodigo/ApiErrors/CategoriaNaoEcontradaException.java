package br.com.zup.CasaDoCodigo.ApiErrors;

public class CategoriaNaoEcontradaException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaNaoEcontradaException(String msg) {
		super(msg);
	}
}
