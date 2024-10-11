package br.edu.atitus.paradigma.servico_de_moeda.repositories;  

import java.util.Optional;  

import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.stereotype.Repository;  
import br.edu.atitus.paradigma.servico_de_moeda.entities.EntidadeMoeda;  

@Repository  
public interface RepositorioMoeda extends JpaRepository<EntidadeMoeda, Integer> {  
    
    Optional<EntidadeMoeda> findByOrigemAndDestino(String origem, String destino);  
}