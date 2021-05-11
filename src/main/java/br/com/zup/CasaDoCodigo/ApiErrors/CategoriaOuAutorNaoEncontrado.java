package br.com.zup.CasaDoCodigo.ApiErrors;

public class CategoriaOuAutorNaoEncontrado extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaOuAutorNaoEncontrado(String message) {
		super(message);
	}
}
