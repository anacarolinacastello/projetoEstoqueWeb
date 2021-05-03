package br.edu.projetoEstoqueWebP.service;

import br.edu.projetoEstoqueWebP.exception.NotFoundException;
import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.model.Permissao;
import br.edu.projetoEstoqueWebP.repository.FuncionarioRespRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioRespService {
      
    @Autowired
    private FuncionarioRespRepository repo;
      
    public List<FuncionarioResp> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();  
    }
      
    public List<FuncionarioResp> findAll(){
          
        return repo.findAll();
    }
    public FuncionarioResp findByEmail(String email){
          
        return repo.findByEmail(email);
    }
    public FuncionarioResp findByUsuario(String usuario){
          
        return repo.findByUsuario(usuario);
    }
    public FuncionarioResp findById(Long id){
          
        Optional<FuncionarioResp>result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Funcionário Responsável não encontrado.");
        }
        return result.get();
    }
      
    public FuncionarioResp save(FuncionarioResp fr){
        
        verificaCpfEmailCadastrado(fr.getCpf(),fr.getEmail());
        removePermissoesNulas(fr);
        try{
            fr.setSenha(new BCryptPasswordEncoder().encode(fr.getSenha()));
            return repo.save(fr);
        } catch(Exception e){
            throw new RuntimeException("Falha ao salvar o Funcionário Responsável");
        }
    }
      
    public FuncionarioResp update(FuncionarioResp fr, String senhaAtual, String novaSenha, String confirmarNovaSenha){
          
        FuncionarioResp obj = findById(fr.getId());
        removePermissoesNulas(fr);
        alterarSenha(obj, senhaAtual, novaSenha, confirmarNovaSenha);
        try {
            fr.setCpf(obj.getCpf());
            fr.setEmail(obj.getEmail());
            fr.setSenha(obj.getSenha());
            return repo.save(fr);
            }catch (Exception e) {
                
                Throwable t = e;
                    while (t.getCause() != null) {
                        t = t.getCause();
                        if (t instanceof ConstraintViolationException) {
                            throw ((ConstraintViolationException) t);
                        }
                    }
                 
                throw new RuntimeException("Falha ao atualizar o Funcionário Responsável");
            }
    }
    public void delete(Long id) {
        FuncionarioResp obj = findById(id);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o Funcionário Responsável");
        }

    }
      
  
    private void verificaCpfEmailCadastrado(String cpf, String email) {
        List<FuncionarioResp> result = repo.findByCpfOrEmail(cpf, email);
        if (!result.isEmpty()) {
        throw new RuntimeException("CPF ou EMAIL já cadastrados."); 
        }
    }


    private void alterarSenha(FuncionarioResp obj, String senhaAtual, String novaSenha, String confirmarNovaSenha) {
        
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        if (!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarNovaSenha.isBlank()) {
            if (!crypt.matches(senhaAtual, obj.getSenha())) {
                throw new RuntimeException("Senha atual está incorreta.");
            }
            if(!novaSenha.equals(confirmarNovaSenha)){    
                
            throw new RuntimeException("Campos de senhas não conferem.");
            }
            obj.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
    
    }
    
  }
    public void removePermissoesNulas(FuncionarioResp fr){
        
        fr.getPermissoes().removeIf( (Permissao p) -> {
            return p.getId()==null;
        });
        if(fr.getPermissoes().isEmpty()){
            throw new RuntimeException("Funcionario deve conter no mínimo 1 permissão.");
        }
    }
    
}
  
    
  
    

