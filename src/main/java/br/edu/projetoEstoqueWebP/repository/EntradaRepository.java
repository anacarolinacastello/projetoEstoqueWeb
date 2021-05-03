package br.edu.projetoEstoqueWebP.repository;

import br.edu.projetoEstoqueWebP.model.Entrada;
import java.util.Calendar;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long>{
    
    public List<Entrada> findByFuncionariorespId(Long funcionariorespId, Pageable page);
  
    public List<Entrada> findByProdutoId(Long produtoId, Pageable page);
    
    public Entrada findByDataHoraEntrada(Calendar dataHoraEntrada);

}
