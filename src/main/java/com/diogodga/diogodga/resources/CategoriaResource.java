package com.diogodga.diogodga.resources;

import com.diogodga.diogodga.domain.Categoria;
import com.diogodga.diogodga.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;
    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
        Categoria obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
