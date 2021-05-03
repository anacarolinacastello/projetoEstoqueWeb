package br.edu.projetoEstoqueWebP;

import br.edu.projetoEstoqueWebP.model.Alocacao;
import br.edu.projetoEstoqueWebP.model.Despache;
import br.edu.projetoEstoqueWebP.model.Entrada;
import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.model.Permissao;
import br.edu.projetoEstoqueWebP.model.Produto;
import br.edu.projetoEstoqueWebP.repository.DespacheRepository;
import br.edu.projetoEstoqueWebP.repository.EntradaRepository;
import br.edu.projetoEstoqueWebP.repository.FuncionarioRespRepository;
import br.edu.projetoEstoqueWebP.repository.PermissaoRepository;
import br.edu.projetoEstoqueWebP.repository.ProdutoRepository;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProjetoEstoqueWebPApplication implements CommandLineRunner{
    @Autowired
    private FuncionarioRespRepository funcionarioRespRepo;
    @Autowired
    private ProdutoRepository produtoRepo;
    @Autowired
    private EntradaRepository entradaRepo;
    @Autowired
    private DespacheRepository despacheRepo;
    @Autowired
    private PermissaoRepository permissaoRepo;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoEstoqueWebPApplication.class, args);
	}
        
        @Override
        public void run(String... args) throws Exception{
            
            //permissoes 
            
            Permissao pe1 = new Permissao();
            pe1.setNome("ADMIN");
            Permissao pe2 = new Permissao();
            pe2.setNome("FUNC");
            
            permissaoRepo.saveAll(List.of(pe1,pe2));
            
            
            //FuncionarioResp
            
            FuncionarioResp fr1 = new FuncionarioResp();
            
            fr1.setPermissoes(List.of(pe1));
            fr1.setNome("Ana");
            fr1.setCpf("368.428.700-89");
            fr1.setEmail("anacasdr@gmail.com");
            fr1.setUsuario("anaca");
            fr1.setSenha(new BCryptPasswordEncoder().encode("12345678"));
            
            
       
            funcionarioRespRepo.save(fr1);
            
            FuncionarioResp fr2 = new FuncionarioResp();
            fr2.setPermissoes(List.of(pe2));
            fr2.setNome("Paula");
            fr2.setCpf("331.450.220-67");
            fr2.setEmail("Paula@gmail.com");
            fr2.setUsuario("paula");
            fr2.setSenha(new BCryptPasswordEncoder().encode("255368"));
            
            funcionarioRespRepo.save(fr2);


            FuncionarioResp fr3 = new FuncionarioResp();
            fr3.setPermissoes(List.of(pe1));
            fr3.setNome("Hiwri");
            fr3.setCpf("856.280.040-65");
            fr3.setEmail("hiwri@gmail.com");
            fr3.setUsuario("hiwri");
            fr3.setSenha("330260");
            
            funcionarioRespRepo.save(fr3);
            
            //Produto
        
            Produto p1 = new Produto();

            p1.setNome("Geladeira");
            p1.setCategoria("Eletrodomestico");
            p1.setPeso((int) 300.00);
            p1.setCodigo("52563322555555522");
         
            
            produtoRepo.save(p1);
            
            Produto p2 = new Produto();

            p2.setNome("Celular");
            p2.setCategoria("Eletronico");
            p2.setPeso((int) 55.00);
            p2.setCodigo("26333322555566669");
            produtoRepo.save(p2);
            
            Produto p3 = new Produto();

            p3.setNome("Chapinha");
            p3.setCategoria("Eletronico");
            p3.setPeso((int) 50.00);
            p3.setCodigo("26333322555563699");
            
            produtoRepo.save(p3);
            
            Produto p4 = new Produto();
            p4.setNome("Maquina de lavar");
            p4.setCategoria("Eletrodomestico");
            p4.setPeso((int) 200.00);
            p4.setCodigo("26333362355563699");
            produtoRepo.save(p4);
            
           

            //Alocacao
            Alocacao a1= new Alocacao();
            a1.setCorredor(2);
            a1.setEstante(5);

            Alocacao a2= new Alocacao();
            a2.setCorredor(5);
            a2.setEstante(4);
            
            //Entrada
            
            Entrada e1 = new Entrada();
        
            e1.setFuncionarioresp(fr1);
            e1.setProduto(p1);
            e1.setAlocacao(a1);
    
            e1.setDataHoraEntrada(Calendar.getInstance());
            
            entradaRepo.save(e1);
            
            //Despache

            Despache d1 = new Despache();
        
            d1.setFuncionarioresp(fr2);
            d1.setProduto(p2);
            d1.setDataHoraDespache(Calendar.getInstance());
            d1.setLocalDeDespache("Campos dos Goytacazes");
         
            despacheRepo.save(d1);
        }

}