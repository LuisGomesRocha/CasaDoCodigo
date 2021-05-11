package br.com.zup.CasaDoCodigo.Categoria;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import br.com.zup.CasaDoCodigo.Validator.Groups;

@Entity
public class Categoria {

	@NotNull(groups = Groups.Categoria.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nome;

	public Categoria() {
	}

	public Categoria(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
