package br.edu.projetoEstoqueWebP.service;

import br.edu.projetoEstoqueWebP.exception.NotFoundException;
import br.edu.projetoEstoqueWebP.model.Despache;
import br.edu.projetoEstoqueWebP.repository.DespacheRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DespacheService {
    
    @Autowired
    private DespacheRepository repo;
      
    public List<Despache> findAll(){
          
        return repo.findAll();
    }
    public List<Despache> findAll(int page, int size, Long funcionarioRespId, Long produtoId){
            
        Pageable p =PageRequest.of(page,size);
        if(funcionarioRespId!=0){
            return repo.findByFuncionariorespId(funcionarioRespId, p);
        }
        if(produtoId!=0){
            return repo.findByProdutoId(produtoId, p);
        }
            
        return repo.findAll(p).toList();
    }
        
    public Despache findById(Long id){
          
        Optional<Despache>obj = repo.findById(id);
        if(obj.isEmpty()){
                
            throw new NotFoundException("Despache não encontrado.");
        }
        return obj.get();
    }
    
    public Despache save(Despache d){

        try{
            return repo.save(d);
            }catch(Exception e){
                Throwable t = e;
                while (t.getCause() != null) { 
                    t = t.getCause();
                    if (t instanceof ConstraintViolationException) {
                        throw ((ConstraintViolationException) t);
                    }
                }
            throw new RuntimeException("Falha ao salvar o Despache");
            }  
             
    }
         
    public Despache update(Despache d){
             
        Despache obj = findById(d.getId());
        try {
            d.setLocalDeDespache(d.getLocalDeDespache());
            d.setFuncionarioresp(d.getFuncionarioresp());
            d.setDataHoraDespache(obj.getDataHoraDespache());
            d.setProduto(d.getProduto());
            return repo.save(d);
        }catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                throw ((ConstraintViolationException) t);
            }
        }
                 
            throw new RuntimeException("Falha ao atualizar o Despache");
        }
    }
        
    public void delete(Long id){
        Despache obj = findById(id);
        try{
            repo.delete(obj);
        }catch(Exception e){
            throw new RuntimeException("Falha ao deletar o Despache");
        }
    }         
}