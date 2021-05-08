package br.com.zup.CasaDoCodigo.Autor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.zup.CasaDoCodigo.Validator.VerificaCampoDuplicado;

import java.util.Locale;

public class AutorRequest {


    @NotBlank
    private String nome;
    @NotBlank @Email
    @VerificaCampoDuplicado(attribute = "email", clazz = Autor.class)
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
