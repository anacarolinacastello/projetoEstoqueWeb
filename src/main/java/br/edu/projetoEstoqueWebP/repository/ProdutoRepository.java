package br.edu.projetoEstoqueWebP.repository;

import br.edu.projetoEstoqueWebP.model.Entrada;
import br.edu.projetoEstoqueWebP.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
    @Query("SELECT p FROM Produto p WHERE p.id = :produtoId")
    public List<Produto> findByProduto(@Param("produtoId")Long produtoId); 
  
    public Produto findByNome(String nome);
    public Produto findByCategoria(String categoria);
    public Produto findByCodigo(String codigo);
    
    @Query("SELECT p FROM Produto p WHERE p.codigo = :produtoCodigo")
    public List<Produto> findByCodigoS(@Param("produtoCodigo")String produtoCodigo); 

    public List<Produto> findByEntradas(Entrada entradas);

}