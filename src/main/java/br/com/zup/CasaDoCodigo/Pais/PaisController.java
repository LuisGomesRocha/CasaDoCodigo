package br.com.zup.CasaDoCodigo.Pais;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paises")
public class PaisController {

	private PaisRepository repository;

	public PaisController(PaisRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public void salvarPaises(@RequestBody PaisRequest paisRequest) {
		repository.save(paisRequest.toPais());
	}
}
