package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.AdresseDto;
import com.exo2.Exercice2.service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
public class AdresseController {
    @Autowired
    private AdresseService adresseService;

    @GetMapping("/{id}")
    @Cacheable(value = "findOneAddress", key = "#id")
    public ResponseEntity<AdresseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(adresseService.findById(id));
    }

    @GetMapping
    @Cacheable(value = "findAllAddress", key = "#page-#amount")
    public ResponseEntity<List<AdresseDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amount) {
        Pageable pageable = PageRequest.of(page, amount);
        return ResponseEntity.ok(adresseService.findAll(pageable));
    }

    @GetMapping("/findBy")
    public ResponseEntity<List<AdresseDto>> findBy(@RequestParam String ville) {
        return ResponseEntity.ok(adresseService.findByVille(ville));
    }
}
