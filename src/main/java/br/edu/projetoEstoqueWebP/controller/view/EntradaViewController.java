package br.edu.projetoEstoqueWebP.controller.view;

import br.edu.projetoEstoqueWebP.model.Entrada;
import br.edu.projetoEstoqueWebP.service.EntradaService;
import br.edu.projetoEstoqueWebP.service.FuncionarioRespService;
import br.edu.projetoEstoqueWebP.service.ProdutoService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(path="/entradas")
public class EntradaViewController {
    
    @Autowired
    private EntradaService service;
    @Autowired 
    private FuncionarioRespService funcionarioRespService;
    @Autowired 
    private ProdutoService produtoService;
    
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("entradas",service.findAll());
        return "entradas";
    }
    @GetMapping(path = "/entrada")
    public String cadastro(Model model) {
        model.addAttribute("entrada", new Entrada());
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());

        return "formEntrada";
    }
    @PostMapping(path = "/entrada")
    public String save(@Valid @ModelAttribute Entrada entrada,BindingResult result, Model model) {
        
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHoraEntrada")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formEntrada";
        }
        entrada.setId(null);
        try {
            service.save(entrada);
            model.addAttribute("msgSucesso", "Entrada cadastrada com sucesso.");
            model.addAttribute("entrada", new Entrada());
            return "formEntrada";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("entrada", e.getMessage()));
            return "formEntrada";
        }
    }
    @GetMapping(path = "/entrada/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entrada", service.findById(id));
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());
        return "formEntrada";
    }

    @PostMapping(path = "/entrada/{id}")
    public String update(@PathVariable("id") Long id,@Valid @ModelAttribute Entrada entrada, BindingResult result, Model model) {
      

        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());


        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHoraEntrada")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formEntrada";
        }

        entrada.setId(id);
        try {
            service.update(entrada);
            model.addAttribute("msgSucesso", "Entrada atualizada com sucesso.");
            model.addAttribute("entrada", entrada);
            return "formEntrada";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("entrada", e.getMessage()));
            return "formEntrada";
        }
    }
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/entradas";
    }

}
