package br.edu.atitus.paradigma.servico_de_moeda.controllers;  

import org.springframework.beans.factory.annotation.Value;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.ExceptionHandler;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  
import br.edu.atitus.paradigma.servico_de_moeda.entities.EntidadeMoeda;  
import br.edu.atitus.paradigma.servico_de_moeda.repositories.RepositorioMoeda;  

@RestController  
@RequestMapping("/conversao")  
public class ControleConversaoMoeda {  
    
    private final RepositorioMoeda repositorio;  

    public ControleConversaoMoeda(RepositorioMoeda repositorio) {  
        this.repositorio = repositorio;  
    }  
    
    @Value("${server.port}")  
    private int porta;  
    
    @GetMapping("/{valor}/{origem}/{destino}")  
    public ResponseEntity<EntidadeMoeda> converterMoeda(  
            @PathVariable double valor,  
            @PathVariable String origem,  
            @PathVariable String destino) throws Exception {  
        
        EntidadeMoeda moeda = repositorio.findByOrigemAndDestino(origem, destino)  
                .orElseThrow(() -> new Exception("Conversão não disponível entre as moedas fornecidas."));  
        
        double valorConvertido = valor * moeda.getFator();  
        moeda.setValorConvertido(valorConvertido);  
        moeda.setAmbiente("Servidor rodando na porta: " + porta);  
        
        return ResponseEntity.ok(moeda);  
    }  

    @ExceptionHandler(Exception.class)  
    public ResponseEntity<String> tratarErro(Exception e) {  
        return ResponseEntity.status(404).body(e.getMessage().replace("\n", " "));  
    }  
}