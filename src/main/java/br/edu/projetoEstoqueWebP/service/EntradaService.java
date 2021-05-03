package br.edu.projetoEstoqueWebP.service;

import br.edu.projetoEstoqueWebP.exception.NotFoundException;
import br.edu.projetoEstoqueWebP.model.Entrada;
import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.model.Produto;
import br.edu.projetoEstoqueWebP.repository.EntradaRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {
    
    @Autowired
    private EntradaRepository repo;
      
    public List<Entrada> findAll(){
          
        return repo.findAll();
    }
    
    public List<Entrada> findAll(int page, int size, Long funcionarioRespId, Long produtoId){
        
        Pageable p =PageRequest.of(page,size);
        if(funcionarioRespId!=0){
            return repo.findByFuncionariorespId(funcionarioRespId, p);
        }
            
        if(produtoId!=0){
            return repo.findByProdutoId(produtoId, p);
        }
            
        return repo.findAll(p).toList();
        }
    
    public Entrada findById(Long id){
          
        Optional<Entrada>obj = repo.findById(id);
        if(obj.isEmpty()){
            throw new NotFoundException("Entrada não encontrada.");
        }
        return obj.get();
    }
    
    
    public Entrada save(Entrada en){
             
        try{
            return repo.save(en);
            } catch(Exception e){
                Throwable t = e;
                while (t.getCause() != null) {
                    t = t.getCause();
                    if (t instanceof ConstraintViolationException) {
                        throw ((ConstraintViolationException) t);
                    }
                }
            throw new RuntimeException("Falha ao salvar a Entrada");
         }
             
    }
         
    public Entrada update(Entrada ee){
             
        Entrada obj = findById(ee.getId());
        try {
            ee.setAlocacao(ee.getAlocacao());
            ee.setFuncionarioresp(ee.getFuncionarioresp());
            ee.setDataHoraEntrada(obj.getDataHoraEntrada());
            ee.setProduto(ee.getProduto());
            return repo.save(ee);
            }catch (Exception e) {
                Throwable t = e;
                while (t.getCause() != null) {
                    t = t.getCause();
                    if (t instanceof ConstraintViolationException) {
                        throw ((ConstraintViolationException) t);
                    }
                }
                throw new RuntimeException("Falha ao atualizar a Entrada");
            }
         }
    
    public void delete(Long id){
        Entrada obj =findById(id);
        try{
            repo.delete(obj);
            }catch(Exception e){
                throw new RuntimeException("Falha ao deletar a Entrada");
            }
        }             
}