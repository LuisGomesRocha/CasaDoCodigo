package br.com.zup.CasaDoCodigo.Estado;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.CasaDoCodigo.Pais.Pais;
import br.com.zup.CasaDoCodigo.Pais.PaisRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	private EstadoRepository estadoRepository;
	private PaisRepository paisRepository;

	public EstadoController(EstadoRepository estadoRepository, PaisRepository paisRepository) {
		this.estadoRepository = estadoRepository;
		this.paisRepository = paisRepository;
	}

	@PostMapping
	public ResponseEntity<?> salvarEstado(@RequestBody @Valid EstadoRequest estadoRequest) {

		Optional<Pais> pais = paisRepository.findById(estadoRequest.getPais().getId());

		if (pais.isPresent()) {

			if (estadoRepository.existsByNomeAndPais(estadoRequest.getNome(), pais.get())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Estado já existe para o pais " + pais.get().getNome());
			}

			Estado estado = estadoRequest.toEstado();
			estado.setPais(pais.get());

			return ResponseEntity.ok(estadoRepository.save(estado));

		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID do país inválido");
	}

}
