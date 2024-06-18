package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    @Cacheable(value = "findAllStudent", key = "#page-#amount")
    public ResponseEntity<List<EtudiantDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amount) {
        Pageable pageable = PageRequest.of(page, amount);
        return ResponseEntity.ok(etudiantService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "findOneStudent", key = "#id")
    public ResponseEntity<EtudiantDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.findById(id));
    }

    @GetMapping("/findBy")
    public ResponseEntity<EtudiantDto> findBy(@RequestParam String nom, @RequestParam String prenom) {
        return ResponseEntity.ok(etudiantService.findOneByNomAndPrenom(nom, prenom));
    }

    @PostMapping
    @Caching(evict = @CacheEvict(value = "findAllStudent"))
    public ResponseEntity<EtudiantDto> save(@RequestBody EtudiantDto etudiantDto) {
        return ResponseEntity.ok(etudiantService.save(etudiantDto));
    }

    @PutMapping("/{id}")
    @Caching(evict = @CacheEvict(value = "findAllStudent"), put = @CachePut(value = "findOneStudent", key = "#etudiantDto.id"))
    public ResponseEntity<EtudiantDto> update(@PathVariable Long id, @RequestBody EtudiantDto etudiantDto) {
        return ResponseEntity.ok(etudiantService.update(id, etudiantDto));
    }

    @DeleteMapping("{id}")
    @Caching(evict = {@CacheEvict(value = "findAllStudent"), @CacheEvict(value = "findOneStudent", key = "#id")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
