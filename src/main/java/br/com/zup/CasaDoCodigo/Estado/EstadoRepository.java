package br.com.zup.CasaDoCodigo.Estado;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.CasaDoCodigo.Pais.Pais;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

	boolean existsByNomeAndPais(String nome, Pais pais);
}
