package br.com.zup.CasaDoCodigo.Pais;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zup.CasaDoCodigo.Validator.VerificaCampoDuplicado;

public class PaisRequest {

	@NotBlank
	@VerificaCampoDuplicado(attribute = "nome", clazz = Pais.class)
	private String nome;

	@JsonCreator
	public PaisRequest(@JsonProperty("nome") String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public Pais toPais() {
		return new Pais(this.nome);
	}
}
