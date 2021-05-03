package br.edu.projetoEstoqueWebP.controller.view;


import br.edu.projetoEstoqueWebP.model.Despache;
import br.edu.projetoEstoqueWebP.service.DespacheService;
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
@RequestMapping(path="/despaches")
public class DespacheViewController {
    
    @Autowired
    private DespacheService service;
    @Autowired 
    private FuncionarioRespService funcionarioRespService;
    @Autowired 
    private ProdutoService produtoService;
    
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("despaches",service.findAll());
        return "despaches";
    }
    @GetMapping(path="/despache")
    public String cadastro(Model model){
        model.addAttribute("despache",new Despache());
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());
        return "formDespache";
    }
     @PostMapping(path = "/despache")
    public String save(@Valid @ModelAttribute Despache despache,BindingResult result, Model model) {
        
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHoraDespache")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formDespache";
        }
        despache.setId(null);
        try {
            service.save(despache);
            model.addAttribute("msgSucesso", "Despache cadastrado com sucesso.");
            model.addAttribute("despache", new Despache());
            return "formDespache";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("despache", e.getMessage()));
            return "formDespache";
        }
    }
    @GetMapping(path = "/despache/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("despache", service.findById(id));
        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());
        return "formDespache";
    }

    @PostMapping(path = "/despache/{id}")
    public String update(@PathVariable("id") Long id,@Valid @ModelAttribute Despache despache, BindingResult result, Model model) {
      

        model.addAttribute("produto", produtoService.findAll());
        model.addAttribute("funcionarioresp", funcionarioRespService.findAll());


        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHoraDespache")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formDespache";
        }

        despache.setId(id);
        try {
            service.update(despache);
            model.addAttribute("msgSucesso", "Despache atualizado com sucesso.");
            model.addAttribute("despache", despache);
            return "formDespache";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("despache", e.getMessage()));
            return "formDespache";
        }
    }
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/despaches";
    }

    
}
