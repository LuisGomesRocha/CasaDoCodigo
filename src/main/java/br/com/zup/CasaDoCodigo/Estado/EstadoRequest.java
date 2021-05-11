package br.com.zup.CasaDoCodigo.Estado;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import br.com.zup.CasaDoCodigo.Pais.Pais;
import br.com.zup.CasaDoCodigo.Validator.Groups;

public class EstadoRequest {

	@NotBlank
	private String nome;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.Pais.class)
	@NotNull
	private Pais pais;

	public EstadoRequest(String nome, Pais pais) {
		this.nome = nome;
		this.pais = pais;
	}

	public String getNome() {
		return nome;
	}

	public Pais getPais() {
		return pais;
	}

	public Estado toEstado() {
		return new Estado(nome, pais);
	}
}
