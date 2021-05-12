# CasaDoCodigo
Nessa tarefa precisamos criar um projeto para atender as funcionalidades da Casa do código, para tal, temos alguns pré requisitos de linguagem de programação e tecnologia, pois precisamos que esse projeto seja evoluído e mantido por anos, portanto é extremamente importante a escolha das mesmas.

<h1 align="center">
    <a href="https://github.com/zup-academy/nosso-cartao-documentacao/tree/master/orange-talent-3/treino-casa-do-codigo">🔗 Casa do Código </a>
</h1>
<p align="center">🚀 Formulário de ideia | Implementação Cadastro Novo Autor - Olá Zupper, este questionário é uma forma de entender o raciocínio que você pretende utilizar para desenvolver a funcionalidade "Cadastro Novo Autor" da casa do código. 
Você deve dissertar sobre como você resolveria a funcionalidade em questão antes de começar a implementar. 🚀 </p>

<h4 align="center"> 
	🚧  🚀 Casa do Código...  🚧
</h4>

### Features

- [x] O instante não pode ser nulo
- [x] O email é obrigatório
- [x] O email tem que ter formato válido
- [x] O nome é obrigatório
- [x] A descrição é obrigatória e não pode passar de 400 caracteres


<p align="justify"> :robot: Por favor descreva como você pretende realizar a implementação deste desafio  :robot: </p>

<p align="justify"> :robot: Para cadastrar um novo autor no sistema será criado a classe Autor, anotado com @Entity que informar que uma classe também é uma entidade, a partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos. Os dados existentes nessa classe será Id, nome, email, createdAt. 
Afim de atender as restrições será utilizado as anotações @Column(nullable = false), sendo esta anotação implementa uma restrição não nula à coluna do banco de dados onde o  banco de dados verifica a restrição quando você insere ou atualiza um registro. Com a notação @Column(nullable = false, length = 400) adiciona obrigatoriedade do campo bem como tamanho máximo de 400 caracteres.
:robot: </p>

<h2 align="center">
    Autor Domain
</h2>

```
@Entity
public class Autor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)//, unique = true)
    private String email;

    @Column(nullable = false, length = 400)
    private String descricao;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
```

<h2 align="center">
    Autor Controller
</h2>

```
@RestController
@RequestMapping("/autores")
public class AutorController {

    private AutorRepository repository;

    public AutorController(AutorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void salvarAutor(@RequestBody @Valid AutorRequest autorRequest) {

        repository.save(autorRequest.toAutor());

    }

}
```

<h2 align="center">
    Autor Repository
</h2>

```
package br.com.zup.CasaDoCodigo.Autor;

import org.springframework.data.repository.CrudRepository;

public interface AutorRepository extends CrudRepository<Autor, Long> {

}
```

<h2 align="center">
    Autor Request
</h2>

```
public class AutorRequest {


    @NotBlank

    private String nome;
    @NotBlank @Email
    @Column (unique = true) 
    private String email;
    @NotBlank @Size(max = 400)
    private String descricao;

    public AutorRequest(@NotBlank String nome,
                        @NotBlank @Email String email,
                        @NotBlank @Size(max = 400) String descricao) {
        this.nome = nome;
        this.setEmail(email);
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    //único ponto de entrada forçando o lowercase para email
    public void setEmail(String email) {
        this.email = email.toLowerCase(Locale.ROOT);
    }

    public Autor toAutor(){
        return new Autor(this.nome, this.email, this.descricao);
    }
}
}
```


<h1 align="center">
    <a href="https://github.com/LuisGomesRocha/CasaDoCodigo/releases/tag/Necessidades">🔗 Necessidades de anotação personalizada... </a>
</h1>


<p align="center">🚀Formulário de implementação do E-mail Autor Único - Olá Zupper, este questionário é uma forma de entender o raciocínio que você utilizou para desenvolver a funcionalidade do aplicativo que você está desenvolvendo na casa do código. 🚀 </p>

### Features

- [x] O email do autor precisa ser único no sistema

<p align="justify"> :robot: A possibilidade de se criar uma anotação personalizada foi introduzida no Java 5 permitindo que metadados sejam escritos diretamente no código. Metadados são, por definição, dados que fazem referência aos próprios dados.:robot: </p>
	
<p align="justify"> :robot:  Desta forma para criar a anotação que garante que o e-mail do autor seja único no sistema, vamos iniciar criando uma classe interface (pode ser entendida como um tipo especial de classe abstrata, onde contém apenas atributos especiais (static e final) e todos os seus métodos são implicitamente abstract e public e não possuem corpo), chamada de VerificaCampoDuplicado. Essa classe vai possuir as anotações de @Retention @Target e @Constraint.
A anotação Retention definirá até quando nossa anotação estará disponível, sendo que precisamos que ela seja executada quando o usuário enviar os seus dados, e isso acontece quando nossa aplicação está rodando, logo precisamos dela em tempo de execução, Runtime. Em seguida a notação Target definirá quais dos elementos que podem ser anotados com essa anotação.:robot: </p>


<h2 align="center">
    VerificaCampoDuplicado
</h2>

```
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {VerificaCampoDuplicadoValidator.class})
public @interface VerificaCampoDuplicado {

    String message() default "{VerificaCampoDuplicado}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String attribute();
    Class<?> clazz();

}

```

<h2 align="center">
    VerificaCampoDuplicadoValidator
</h2>

```
public class VerificaCampoDuplicadoValidator
        implements ConstraintValidator<VerificaCampoDuplicado,Object> {

    private String campo;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(VerificaCampoDuplicado parameters) {

        this.campo = parameters.attribute();
        this.clazz = parameters.clazz();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {

        Query query = manager.createQuery("SELECT 1 FROM "+ clazz.getName() + " where " + campo + " =:valor");
        query.setParameter("valor", objetoValidacao);

        var resultList = query.getResultList();

        return resultList.size() > 0 ? false: true;

    }
}

```

<h1 align="center">
    <a href="https://github.com/LuisGomesRocha/CasaDoCodigo/releases/tag/V2">🔗 Cadastro de uma categoria </a>
</h1>

<p align="center">🚀Formulário de ideia | Implementação Cadastro de uma categoria - Olá Zupper, este questionário é uma forma de entender o raciocínio que você pretende utilizar para desenvolver a funcionalidade "Cadastro de uma categoria" da casa do código.  🚀 </p>

### Features

- [x] Toda categoria precisa de um nome
- [x] O nome é obrigatório
- [x] O nome não pode ser duplicado

### Resultado esperado
- [x] Uma nova categoria cadastrada no sistema e status 200 retorno
- [x] Caso alguma restrição não seja atendida, retorne 400 e um json informando os problemas de validação

<p align="justify"> :robot: Para cadastrar uma nova categoria no sistema será criada a classe Categoria, anotada com @Entity que informar que uma classe também é uma entidade, a partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos. Os dados existentes nessa classe será Id e nome.
Afim de atender as restrições será utilizado as anotações @Column(unique = true), sendo esta anotação usada para garantir um valor único criado em seu banco de dados, não permitindo valores repetidos em sua coluna.
:robot: </p>
<p align="justify"> :robot: 
Para atender os casos em que alguma restrição não seja atendida, personalizando o JSON (informando os problemas de validação), será criado duas classes denominadas CategoriaOuAutorNaoEncontrado e ErrosHandle, onde na primeira será estendido os métodos da classe RuntimeException capturando a mensagem de erro, e no segundo momento nos casos de MethodArgumentNotValidException o status de retorno será “BAD REQUEST” com uma mensagem personalizada dizendo: "Categoria ID ou Autor ID", "Id(s) não encontrado!"
:robot: </p>

<h2 align="center">
    CategoriaOuAutorNaoEncontrado
</h2>

```
public class CategoriaOuAutorNaoEncontrado extends RuntimeException{
    public CategoriaOuAutorNaoEncontrado(String message) {
        super(message);
    }
}
```

<h2 align="center">
    ErrosHandle
</h2>

```
public class ErrosHandle {

    private MessageSource messageSource;


    public ErrosHandle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrosResponseDto> autorValidationError(MethodArgumentNotValidException ex){

        List<ErrosResponseDto> erros = new ArrayList<>();

        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();

        errorList.forEach(e->{
                    String message = messageSource.getMessage(e,LocaleContextHolder.getLocale());
                    erros.add(new ErrosResponseDto(e.getField(),message));
                });

        return erros;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoriaOuAutorNaoEncontrado.class)
    public ErrosResponseDto CategoriaOuAutorNaoEncontrado(CategoriaOuAutorNaoEncontrado ex){

        ErrosResponseDto erro;
        erro = new ErrosResponseDto("Categoria ID ou Autor ID", "Id(s) não encontrado!");
        return erro;

    }

}
```

<h1 align="center">
    <a href="https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/orange-talent-3/treino-casa-do-codigo/0-0-4.1-criacao-validador-generico.md">🔗 Criação validador genérico... </a>
</h1>


<p align="center">🚀Tanto para o cadastro do autor quanto para o cadastro da categoria, foi necessário realizar uma validação de valor único no sistema. Neste caso, só muda um detalhe da query que estamos executando para fazer a verificação. E agora, será que você consegue criar seu validador customizado para reutilizá-lo nas validações de email de autor e nome de categoria? 🚀 </p>

### Features

- [x] Criar um validador genérico para autor e categoria

<p align="justify"> :robot: A classe VerificaCampoDuplicadoValidator irá implementar uma query que irá buscar o valor no bando de dados para que seja comparado ao email de entada retornado a presença ou não do email pesquisado no banco de dados. Uma vez a notação criada a mesma será implementada na classe AutorRequest @VerificaCampoDuplicado(attribute = "email", clazz = Autor.class). A mesma lógica pode ser utilizada no cadastro de categoria.:robot: </p>


<h2 align="center">
    Exemplo de aplicação: AutorRequest e CategoriaRequest:
</h2>

```	
	Ex.:   
	@VerificaCampoDuplicado(attribute = "nome",clazz = Categoria.class)
	private String nome;
	
	      OU
	@NotBlank @Email
	@VerificaCampoDuplicado(attribute = "email", clazz = Autor.class)
	private String email;   
	
```



<h2 align="center">
    VerificaCampoDuplicadoValidator
</h2>

```
public class VerificaCampoDuplicadoValidator
        implements ConstraintValidator<VerificaCampoDuplicado,Object> {

    private String campo;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(VerificaCampoDuplicado parameters) {

        this.campo = parameters.attribute();
        this.clazz = parameters.clazz();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {

        Query query = manager.createQuery("SELECT 1 FROM "+ clazz.getName() + " where " + campo + " =:valor");
        query.setParameter("valor", objetoValidacao);

        var resultList = query.getResultList();

        return resultList.size() > 0 ? false: true;

    }
}
```

<h1 align="center">
    <a href="https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/orange-talent-3/treino-casa-do-codigo/0-0-5-criar-um-novo-livro.md">🔗 Livro - Criar, listar e detalhe... </a>
</h1>


<p align="center">🚀Olá Zupper, este questionário é uma forma de entender o raciocínio que você pretende utilizar para desenvolver a funcionalidade "Cadastro de um novo livro" , funcionalidade "Exibir lista livros"  e "Exibir lista livros" da casa do código. 🚀 </p>

### Necessidades
- [x] Um título
- [x] Um resumo do que vai ser encontrado no livro
- [x] Um sumário de tamanho livre. O texto deve entrar no formato markdown, que é uma string. Dessa forma ele pode ser formatado depois da maneira apropriada.
- [x] Preço do livro
- [x] Número de páginas
- [x] Isbn(identificador do livro)
- [x] Data que ele deve entrar no ar(de publicação)
- [x] Um livro pertence a uma categoria
- [x] Um livro é de um autor
- [x] Para que seja fácil pegar um id do livro, vamos exibir a lista de livros cadastrados
- [x] Ter um endpoint que em função de um id de livro retorne os detalhes necessários para montar a página

### Restrições
- [x] Título é obrigatório
- [x] Título é único
- [x] Resumo é obrigatório e tem no máximo 500 caracteres
- [x] Sumário é de tamanho livre.
- [x] Preço é obrigatório e o mínimo é de 20
- [x] Número de páginas é obrigatória e o mínimo é de 100
- [x] Isbn é obrigatório, formato livre
- [x] Isbn é único
- [x] Data que vai entrar no ar precisa ser no futuro
- [x] A categoria não pode ser nula
- [x] O autor não pode ser nulo
- [x] Se o id não existir é para retornar 404

	
<p align="justify"> :robot: Para cadastrar um novo livro no sistema será criado a classe Lutor, anotado com @Entity que informar que uma classe também é uma entidade, a partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos. Os dados existentes nessa classe será Id, titulo, subTitulo, resumo, sumario, preco, paginas, isbn e dataPublicacao. A classe livro terá um modelo relacional (uma associação muitos-para-um (N:1) é quando temos várias tuplas de uma tabela referenciando uma tupla de uma tabela qualquer), @ManyToOne com as classes Categoria e Autor.:robot: </p>

<p align="justify"> :robot: Teremos também a interface  LivroRepository que vai receber os métodos da classe JpaRepository, bem como a classe LivroController que vai ter os métodos de salvar elistar os livros cadastrados. Por fim na classe LivroRequest e LivroResponse, irá ocorrer a conversão do objeto relacionando com classe Autor e Categoria, bem como limitando as informações e retorno após a requisição do "endpoint" dos dados cadastrados. :robot: </p>


<h2 align="center">
    Livro
</h2>

```

@Entity
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String titulo;
    @Column(nullable = false)
    private String subTitulo;
    @Column(nullable = false, length = 500)
    private String resumo;
    @Column(columnDefinition = "text null")
    private String sumario;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false)
    private Integer paginas;
    @Column(nullable = false, unique = true)
    private String isbn;
    private LocalDate dataPublicacao;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Autor autor;

```


<h2 align="center">
    LivroRequest
</h2>

```

public class LivroRequest {

    @NotBlank
    @VerificaCampoDuplicado(attribute = "titulo", clazz = Livro.class)
    private String titulo;

    @NotBlank
    private String subTitulo;

    @NotBlank @Size(max = 500)
    private String resumo;

    private String sumario;

    @DecimalMin("20.0")
    private BigDecimal preco;

    @Min(100)
    private Integer nPaginas;

    @NotBlank
    @VerificaCampoDuplicado(attribute = "isbn",clazz = Livro.class)
    private String isbn;

    @Future @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataPublicacao;

    @Valid
    @NotNull //Classe Categoria
    @ConvertGroup(from = Default.class, to = Groups.Categoria.class)
    private Categoria categoria;

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.Autor.class)
    private Autor autor;

                              .
                              .
                              .

    public Livro toLivro(Categoria categoria, Autor autor){

        return new Livro(this.titulo,this.subTitulo, this.resumo,this.sumario,this.preco,this.nPaginas,
                    this.isbn,this.dataPublicacao,categoria,autor);

    }
}
```


<h2 align="center">
    LivroController
</h2>

```

@RestController
@RequestMapping("/livros")
public class LivroController {

    private LivroRepository repository;
    private CategoriaRepository categoriaRepository;
    private AutorRepository autorRepository;

    public LivroController(LivroRepository repository,
                           CategoriaRepository categoriaRepository,
                           AutorRepository autorRepository) {

        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;

    }

    @PostMapping
    public ResponseEntity<?> salvarLivro(@RequestBody @Valid LivroRequest livroRequest)  {

        //verificar a categoria
        Optional<Categoria> categoria = categoriaRepository.findById(livroRequest.getCategoria().getId());
        Optional<Autor> autor = autorRepository.findById(livroRequest.getAutor().getId());
        if(categoria.isPresent() && autor.isPresent()){
            return ResponseEntity.ok(repository.save(livroRequest.toLivro(categoria.get(),autor.get())));
        }

        throw new CategoriaOuAutorNaoEncontrado("Id da Categoria ou do Autor nao encontrado!");

    }

    @GetMapping
    public List<LivroResponse> listarTodos(){
        LivroResponse livroResponse = new LivroResponse();
        return livroResponse.toLivroResponse(repository.findAll());
    }

}
```

<h2 align="center">
    LivroRepository
</h2>

```
public interface LivroRepository extends JpaRepository<Livro, Long> {
}

```

<h2 align="center">
    LivroResponse
</h2>

```
public class LivroResponse {
    private Long id;
    private String nome;

    public LivroResponse() {
    }

    public LivroResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<LivroResponse> toLivroResponse(List<Livro> livros) {

        List<LivroResponse> livroResponses = livros.stream()
                .map(e->new LivroResponse(e.getId(),e.getTitulo()))
                .collect(Collectors.toList());
        return livroResponses;

    }
}

```

<h1 align="center">
    <a href="https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/orange-talent-3/treino-casa-do-codigo/0-0-8-cadastro-de-pais-e-estados-do-pais.md">🔗 Livro - Precisamos de um cadastro simples de países e seus respectivos estados.... </a>
</h1>


<p align="center">🚀Olá Zupper, este questionário é uma forma de entender o raciocínio que você pretende utilizar para desenvolver a funcionalidade "Cadastro de país e estados" da casa do código.  🚀 </p>

### Necessidades
- [x] O nome é obrigatório

### Restrições
- [x] O nome é único
- [x] o nome é único para o mesmo país (Estados)
- [x] o país é obrigatório (Estados)


<p align="justify"> :robot: Para cadastrar um novo País será criado a classe País, anotado com @Entity que informar que uma classe também é uma entidade, a partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos. Os dados existentes nessa classe será Id e nome. A classe Estados também será anotada com @Entity, porém os dados existentes nessa classe serão id e nome bem como uma relação @ManyToOne com a classe País.:robot: </p>
	
	



