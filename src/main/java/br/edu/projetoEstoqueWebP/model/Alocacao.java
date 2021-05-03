package br.edu.projetoEstoqueWebP.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Embeddable
public class Alocacao implements Serializable{
 
    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    @Min(0)
    @Digits(integer= 7,fraction = 0, message = "Coreddor deve conter um número inteiro não negativo.")
    private int corredor;
    @Column(nullable = false)
    @Min(0)
    @Digits(integer= 7,fraction = 0, message = "Estante deve conter um número inteiro não negativo.")
    private int estante;

    public int getCorredor() {
        return corredor;
    }

    public void setCorredor(int corredor) {
        this.corredor = corredor;
    }

    public int getEstante() {
        return estante;
    }

    public void setEstante(int estante) {
        this.estante = estante;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.corredor;
        hash = 79 * hash + this.estante;
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
        final Alocacao other = (Alocacao) obj;
        if (this.corredor != other.corredor) {
            return false;
        }
        if (this.estante != other.estante) {
            return false;
        }
        return true;
    }

    public Alocacao() {
        
    }
    
    
}
