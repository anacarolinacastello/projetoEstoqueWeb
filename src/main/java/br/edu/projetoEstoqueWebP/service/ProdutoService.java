package br.edu.projetoEstoqueWebP.service;

import br.edu.projetoEstoqueWebP.exception.NotFoundException;
import br.edu.projetoEstoqueWebP.model.Produto;
import br.edu.projetoEstoqueWebP.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repo;
    
    public List<Produto> findAll(int page, int size){
          Pageable p = PageRequest.of(page, size);
          return repo.findAll(p).toList();  
    }
    public List<Produto> findAll(){
          
        return repo.findAll();
    }
      
    public Produto findById(Long id){
          
        Optional<Produto>result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Produto não encontrado.");
        }
        return result.get();
    }
      
    public Produto findByCategoria(String categoria) {
          
        return repo.findByCategoria(categoria); 
    }
      
    public Produto findByNome(String nome) {
          
        return repo.findByNome(nome);
    }
    public Produto findByCodigo(String codigo) {
          
        return repo.findByCodigo(codigo);
    }
    public Produto save(Produto p){
          
        verificaCodigo(p.getCodigo());
        try{
            return repo.save(p);
        } catch(Exception e){
            throw new RuntimeException("Falha ao salvar o Produto");
        }
    }
             
    public Produto update(Produto p){
          
        // o usuario pode alterar todos os dados de produto
        
        try {
            p.setCategoria(p.getCategoria());
            p.setCodigo(p.getCodigo());
            p.setNome(p.getNome());
            p.setPeso(p.getPeso());
            
            return repo.save(p);
            }catch (Exception e) {
               throw new RuntimeException("Falha ao atualizar o Produto");
            }
    }
       
    private void verificaCodigo(String codigo) {
        
        List<Produto> result = repo.findByCodigoS(codigo);
        if (!result.isEmpty()) {
        
            throw new  RuntimeException("Codigo já cadastrado."); 
        }
    }
    public void delete(Long id) {
           
        Produto obj = findById(id);
         //  verificaExclusãoProdutosComEntrada(obj);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            
            throw new RuntimeException("Falha ao excluir o Produto");
        }

    }
        
}