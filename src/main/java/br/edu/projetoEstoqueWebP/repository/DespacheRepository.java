package br.edu.projetoEstoqueWebP.repository;

import br.edu.projetoEstoqueWebP.model.Despache;
import java.util.Calendar;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespacheRepository extends JpaRepository<Despache, Long>{
    
    public List<Despache> findByFuncionariorespId(Long funcionariorespId, Pageable page);
  
    public List<Despache> findByProdutoId(Long produtoId, Pageable page);
    
    public List<Despache> findByDataHoraDespache(Calendar dataHoraDespache,Pageable page);

}
