package br.edu.projetoEstoqueWebP.repository;


import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRespRepository extends JpaRepository<FuncionarioResp, Long>{
    @Query("SELECT fr FROM FuncionarioResp fr WHERE fr.cpf = :cpf OR fr.email = :email")
    public List<FuncionarioResp> findByCpfOrEmail(@Param("cpf") String cpf, @Param("email") String email);
    
    public FuncionarioResp findByEmail(String email);
    
    public FuncionarioResp findByUsuario(String usuario);
    
}