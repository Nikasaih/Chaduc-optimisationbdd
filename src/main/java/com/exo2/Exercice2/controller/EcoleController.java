package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.EcoleDto;
import com.exo2.Exercice2.service.EcoleService;
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
@RequestMapping("/ecoles")
public class EcoleController {
    @Autowired
    private EcoleService ecoleService;

    @GetMapping
    @Cacheable(value = "findAllSchool", key = "#page-#amount")
    public ResponseEntity<List<EcoleDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amount) {
        Pageable pageable = PageRequest.of(page, amount);
        return ResponseEntity.ok(ecoleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "findOneSchool", key = "#id")
    public ResponseEntity<EcoleDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ecoleService.findById(id));
    }

    @GetMapping("/findBy")
    public ResponseEntity<List<EcoleDto>> findByNomContainingIgnoreCase(@RequestParam String nom) {
        return ResponseEntity.ok(ecoleService.findByNomEtudiant(nom));
    }

    @PostMapping
    @Caching(evict = {
            @CacheEvict(value = "findAllSchool"),
            @CacheEvict(value = "findAllAddress")
    })
    public ResponseEntity<EcoleDto> save(@RequestBody EcoleDto ecoleDto) {
        return ResponseEntity.ok(ecoleService.save(ecoleDto));
    }
}
