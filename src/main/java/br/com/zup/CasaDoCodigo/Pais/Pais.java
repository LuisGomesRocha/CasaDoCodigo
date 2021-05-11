package br.com.zup.CasaDoCodigo.Pais;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import br.com.zup.CasaDoCodigo.Validator.Groups;

@Entity
public class Pais {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = Groups.Pais.class)
	private Long id;
	@Column(nullable = false, unique = true)
	private String nome;

	public Pais() {
	}

	public Pais(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + " Nome : " + this.nome;
	}

}
