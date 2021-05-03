package br.edu.projetoEstoqueWebP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
public class Produto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Nome do produto obrigatório.")   
    @Length(max =50, message= "Nome do produto deve ter máximo 50 caracteres.")
    private String nome;
    @Column(nullable = false)
    @NotBlank(message = "Categoria obrigatória.")   
    @Length(max =50, message= "Categoria deve ter máximo 50 caracteres.")
    private String categoria;
    @Column(nullable = false)
    @Min(0)
    @Digits(integer= 7, fraction = 3, message = "Peso não pode conter números negativo.")
    private int peso;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Código de barras obrigatório.")   
    @Length(max =50, message= "Código de barras deve ter máximo 50 caracteres.")
    private String codigo;
    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    @Valid
    private List<Entrada> entradas = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    @Valid
    private List<Despache> despaches= new ArrayList<>();

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Produto() {
        
    }
}



