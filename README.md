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

<p align="justify"> :robot: Por fim a classe VerificaCampoDuplicadoValidator irá implementar uma query que irá buscar o valor no bando de dados para que seja comparado ao email de entada retornado a presença ou não do email pesquisado no banco de dados. Uma vez a notação criada a mesma será implementada na classe AutorRequest @VerificaCampoDuplicado(attribute = "email", clazz = Autor.class). :robot: </p>


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
