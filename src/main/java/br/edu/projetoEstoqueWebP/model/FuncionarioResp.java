package br.edu.projetoEstoqueWebP.model;

import br.edu.projetoEstoqueWebP.annotation.EmailValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(value = "senha",allowGetters=false, allowSetters = true)
public class FuncionarioResp implements Serializable{
    
    //Atributos que vieram da antiga classe abstrata Funcionario
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    @NotBlank(message = "Nome obrigatório.")   
    @Length(max = 100, message= "Nome deve ter máximo 100 caracteres.")
    private String nome;
    @Column(nullable = false,length = 100, unique = true, updatable = false)
    @NotBlank(message = "E-mail obrigatório.")
    @EmailValidation(message = "Email inválido.")
    private String email;
    @Column(nullable = false,length = 14, unique = true, updatable = false)
    @CPF(message = "CPF inválido.")
    private String cpf;
   
    @Column(length = 10,nullable = false)
    @NotBlank(message = "Usuário obrigatório.")
    @Length(max = 10,message = "Usuário deve ter no máximo 10 caracteres.")
    private String usuario;
    @Column(nullable = false)
    @NotBlank(message = "Senha obrigatória.")
    private String senha;
    
    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioresp")
    @Valid
    private List<Entrada> entradas = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "funcionarioresp")
    @Valid
    private List<Despache> despaches = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @Size(min = 1, message = "Funcionário deve ter no minimo 1 permissão.")
    private List<Permissao> permissoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<Despache> getDespaches() {
        return despaches;
    }

    public void setDespaches(List<Despache> despaches) {
        this.despaches = despaches;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FuncionarioResp other = (FuncionarioResp) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    public FuncionarioResp() {
    }
    
}