package br.edu.projetoEstoqueWebP.controller.apirest;


import br.edu.projetoEstoqueWebP.model.FuncionarioResp;
import br.edu.projetoEstoqueWebP.service.FuncionarioRespService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/apirest/funcionarioresp")
public class FuncionarioRespApiRestController {
    
    @Autowired
    private FuncionarioRespService service;
    
    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        
        return ResponseEntity.ok(service.findAll(page, size));
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id){
        
        return ResponseEntity.ok(service.findById(id));    
    }
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody FuncionarioResp funcionarioResp){
        
        funcionarioResp.setId(null);
        service.save(funcionarioResp);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioResp); 
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody FuncionarioResp funcionarioResp){
        
        funcionarioResp.setId(id);
        service.update(funcionarioResp, "","","");
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        
        service.delete(id);
        
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(path = "/{id}/alterarSenha")
    public ResponseEntity alterarSenha(@PathVariable("id") Long id,
            @RequestParam(name = "senhaAtual", defaultValue = "", required = true) String senhaAtual,
            @RequestParam(name = "novaSenha", defaultValue = "", required = true) String novaSenha,
            @RequestParam(name = "confirmarNovaSenha", defaultValue = "", required = true) String confirmarNovaSenha){
        
        FuncionarioResp fr = service.findById(id);
        service.update(fr, senhaAtual, novaSenha, confirmarNovaSenha);
        return ResponseEntity.ok().build();
    }
        
}