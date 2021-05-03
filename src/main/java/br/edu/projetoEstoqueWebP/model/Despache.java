package br.edu.projetoEstoqueWebP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Objects;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Despache implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Local de despache obrigatório.")
    @Length(max = 200, message = "Local de despache deve ter no máximo 200 caracteres")
    private String localDeDespache;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message="Data despache é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyy-MM-dd hh:mm:ss")
    private Calendar dataHoraDespache;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    @NotNull(message = "Funcionário obrigatório.")
    private FuncionarioResp funcionarioresp;
    @OneToOne
    @JoinColumn(nullable=false)
    @NotNull(message = "Produto obrigatório.")
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuncionarioResp getFuncionarioresp() {
        return funcionarioresp;
    }

    public void setFuncionarioresp(FuncionarioResp funcionarioresp) {
        this.funcionarioresp = funcionarioresp;
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getLocalDeDespache() {
        return localDeDespache;
    }

    public void setLocalDeDespache(String localDeDespache) {
        this.localDeDespache = localDeDespache;
    }

    public Calendar getDataHoraDespache() {
        return dataHoraDespache;
    }

    public void setDataHoraDespache(Calendar dataHoraDespache) {
        this.dataHoraDespache = dataHoraDespache;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Despache other = (Despache) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Despache() {
    }
    
    
    
}




