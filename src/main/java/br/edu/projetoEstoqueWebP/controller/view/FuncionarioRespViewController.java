package br.edu.projetoEstoqueWebP.controller.view;


import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.model.Permissao;
import br.edu.projetoEstoqueWebP.repository.PermissaoRepository;
import br.edu.projetoEstoqueWebP.service.FuncionarioRespService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/funcionarioresp")
public class FuncionarioRespViewController {
    
    @Autowired
    private FuncionarioRespService service;
    @Autowired
    private PermissaoRepository permissaoRepo;
        
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("funcionarioresp",service.findAll());
        
        return "funcionarioresp";
    }
    @GetMapping(path="/funcionarioResp")
    public String cadastro(Model model){
        model.addAttribute("funcionarioResp",new FuncionarioResp());
        model.addAttribute("permissoes",permissaoRepo.findAll());
        return "formFuncionarioResp";
    }
 @PostMapping(path = "/funcionarioResp")
    public String save(@Valid @ModelAttribute FuncionarioResp funcionarioResp,BindingResult result,@RequestParam("confirmarSenha") String confirmarSenha,Model model) {

        model.addAttribute("permissoes",permissaoRepo.findAll());
      
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionarioResp";
        }

        if (!funcionarioResp.getSenha().equals(confirmarSenha)) {
            model.addAttribute("msgErros", new ObjectError("funcionarioResp", "Senha e confirmar senha devem ser iguais!"));
            return "formFuncionarioResp";
        }

        funcionarioResp.setId(null);
        try {
            service.save(funcionarioResp);
            model.addAttribute("msgSucesso", "Funcionário cadastrado com sucesso.");
            model.addAttribute("funcionarioResp", new FuncionarioResp());
            return "formFuncionarioResp";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionarioResp", e.getMessage()));
            return "formFuncionarioResp";
        }
    }
   
    @GetMapping(path = "/funcionarioResp/{id}")
    public String alterar(@PathVariable("id") Long id,Model model) {
        model.addAttribute("funcionarioResp", service.findById(id));
        model.addAttribute("permissoes",permissaoRepo.findAll());
        return "formFuncionarioResp";
    }

    @PostMapping(path = "/funcionarioResp/{id}")
    public String update(@Valid @ModelAttribute FuncionarioResp funcionarioResp, BindingResult result, @PathVariable("id") Long id, Model model) { 
        
        model.addAttribute("permissoes",permissaoRepo.findAll());

        List<FieldError> list = new ArrayList<>();
        for(FieldError fe : result.getFieldErrors()){
            if(!fe.getField().equals("senha")){
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formFuncionarioResp";
        }

        funcionarioResp.setId(id);
        try {
            service.update(funcionarioResp, "", "", "");
            model.addAttribute("msgSucesso", "Funcionário Responsavel atualizado com sucesso.");
            model.addAttribute("funcionarioResp", funcionarioResp);
            return "formFuncionarioResp";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionarioResp", e.getMessage()));
            return "formFuncionarioResp";
        }
    }
    @GetMapping(path = "/{id}/deletar")
        public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/funcionarioresp";

    }
        
    //meus dados
    @GetMapping(path = "/meusdados")
    public String getMeusDados(@AuthenticationPrincipal User user, Model model){
        FuncionarioResp funcionarioresp = service.findByUsuario(user.getUsername());
        model.addAttribute("funcionarioResp", funcionarioresp);
        return "formMeusDados";
    }
    
     @PostMapping(path = "/meusdados")
    public String updateMeusDados(@Valid @ModelAttribute FuncionarioResp funcionarioresp,BindingResult result,
            @AuthenticationPrincipal User user,
            @RequestParam("senhaAtual") String senhaAtual,
            @RequestParam("novaSenha") String novaSenha,
            @RequestParam("confirmarNovaSenha") String confirmarNovaSenha,
            Model model){
        
        List<FieldError> list = new ArrayList<>();
        for(FieldError fe : result.getFieldErrors()){
            if(!fe.getField().equals("senha") && !fe.getField().equals("permissoes") ){
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formMeusDados";
        }

        FuncionarioResp funcionarioRespBD = service.findByUsuario(user.getUsername());
        if(!funcionarioRespBD.getId().equals(funcionarioresp.getId())){
            throw new RuntimeException("Acesso negado.");
        }
        try {
            funcionarioresp.setPermissoes(funcionarioRespBD.getPermissoes());
            service.update(funcionarioresp, senhaAtual, novaSenha, confirmarNovaSenha);
            model.addAttribute("msgSucesso", "Funcionário atualizado com sucesso.");
            model.addAttribute("funcionarioResp", funcionarioresp);
            return "formMeusDados";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionarioResp", e.getMessage()));
            return "formMeusDados";
        }
    }
   
    
}