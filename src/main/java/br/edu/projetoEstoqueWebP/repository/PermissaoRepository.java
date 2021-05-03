package br.edu.projetoEstoqueWebP.repository;

import br.edu.projetoEstoqueWebP.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
    
}