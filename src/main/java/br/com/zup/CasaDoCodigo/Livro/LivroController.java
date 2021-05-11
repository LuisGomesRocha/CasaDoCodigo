package br.com.zup.CasaDoCodigo.Livro;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.CasaDoCodigo.ApiErrors.CategoriaOuAutorNaoEncontrado;
import br.com.zup.CasaDoCodigo.Autor.Autor;
import br.com.zup.CasaDoCodigo.Autor.AutorRepository;
import br.com.zup.CasaDoCodigo.Categoria.Categoria;
import br.com.zup.CasaDoCodigo.Categoria.CategoriaRepository;

@RestController
@RequestMapping("/livros")
public class LivroController {

	private LivroRepository repository;
	private CategoriaRepository categoriaRepository;
	private AutorRepository autorRepository;

	public LivroController(LivroRepository repository, CategoriaRepository categoriaRepository,
			AutorRepository autorRepository) {

		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
		this.autorRepository = autorRepository;

	}

	@PostMapping
	public ResponseEntity<?> salvarLivro(@RequestBody @Valid LivroRequest livroRequest) {

		// verificar a categoria
		Optional<Categoria> categoria = categoriaRepository.findById(livroRequest.getCategoria().getId());
		Optional<Autor> autor = autorRepository.findById(livroRequest.getAutor().getId());
		if (categoria.isPresent() && autor.isPresent()) {
			return ResponseEntity.ok(repository.save(livroRequest.toLivro(categoria.get(), autor.get())));
		}

		throw new CategoriaOuAutorNaoEncontrado("Id da Categoria ou do Autor nao encontrado!");

	}

	@GetMapping
	public List<LivroResponse> listarTodos() {
		LivroResponse livroResponse = new LivroResponse();
		return livroResponse.toLivroResponse(repository.findAll());
	}

}
