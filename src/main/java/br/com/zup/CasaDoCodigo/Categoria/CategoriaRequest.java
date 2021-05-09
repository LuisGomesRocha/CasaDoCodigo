package br.com.zup.CasaDoCodigo.Categoria;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.zup.CasaDoCodigo.Validator.VerificaCampoDuplicado;

public class CategoriaRequest {

    @NotBlank
    @VerificaCampoDuplicado(attribute = "nome",clazz = Categoria.class)
    private String nome;

    @JsonCreator
    public CategoriaRequest(@JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Categoria toCategoria(){
        return new Categoria(this.nome);
    }
}
