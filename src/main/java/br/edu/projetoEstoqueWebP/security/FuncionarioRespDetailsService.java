package br.edu.projetoEstoqueWebP.security;



import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.model.Permissao;
import br.edu.projetoEstoqueWebP.repository.FuncionarioRespRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioRespDetailsService implements UserDetailsService{
    
    @Autowired
    private FuncionarioRespRepository repo;
    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        
            
        FuncionarioResp funcionarioresp = repo.findByUsuario(usuario);
        
        if(funcionarioresp==null){
            throw new UsernameNotFoundException("Usuario "+usuario+" não encontrado!");
        }
        return new User(funcionarioresp.getUsuario(),funcionarioresp.getSenha(),getAuthorities(funcionarioresp.getPermissoes()));


    }
    private List<GrantedAuthority> getAuthorities(List<Permissao> lista){
        
        List<GrantedAuthority> l = new ArrayList<>();
        for(Permissao p : lista){
            
            l.add(new SimpleGrantedAuthority("ROLE_"+p.getNome()));
            
            
        }
        return l;
        
    }
}