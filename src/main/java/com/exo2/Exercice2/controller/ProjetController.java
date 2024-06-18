package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.dto.ProjetDto;
import com.exo2.Exercice2.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projets")
public class ProjetController {
    @Autowired
    private ProjetService projetService;

    @GetMapping
    @Cacheable(value = "findAllProject", key = "#page-#amount")
    public ResponseEntity<List<ProjetDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amount) {
        Pageable pageable = PageRequest.of(page, amount);
        return ResponseEntity.ok(projetService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "findOneProject", key = "#id")
    public ResponseEntity<ProjetDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findById(id));
    }

    @PostMapping
    @Caching(evict = @CacheEvict(value = "findAllProject"))
    public ResponseEntity<ProjetDto> save(@RequestBody ProjetDto projetDto) {
        return ResponseEntity.ok(projetService.save(projetDto));
    }

    @GetMapping("/{id}/etudiants")
    public ResponseEntity<List<EtudiantDto>> findEtudiantsByProjetId(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findEtudiantsByProjetId(id));
    }
}
