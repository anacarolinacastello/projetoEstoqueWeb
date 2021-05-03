package br.edu.projetoEstoqueWebP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Objects;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Entrada implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message="Data entrada é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyy-MM-dd hh:mm:ss")
    private Calendar dataHoraEntrada;
    
   
    @ManyToOne
    @JoinColumn(nullable=false)
    @NotNull(message = "Funcionário obrigatório.")
    private FuncionarioResp funcionarioresp;
    @ManyToOne
    @JoinColumn(nullable=false)
    @NotNull(message = "Produto obrigatório.")
    private Produto produto;
    
    @Embedded
    @NotNull(message = "Alocação obrigatória.")
    @Valid
    private Alocacao alocacao;

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



    public Calendar getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(Calendar dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

   


   
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

  
    public Alocacao getAlocacao() {
        return alocacao;
    }

    public void setAlocacao(Alocacao alocacao) {
        this.alocacao = alocacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
   
    public Entrada() {
    }
    
}