package com.diogodga.diogodga.resources;

import com.diogodga.diogodga.domain.Produto;
import com.diogodga.diogodga.dto.ProdutoDTO;
import com.diogodga.diogodga.resources.utils.URL;
import com.diogodga.diogodga.services.ProdutoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;
    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca por id")
    public ResponseEntity<Produto> find(@PathVariable Integer id){
        Produto obj = produtoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation(value="Retorna todos produtos com paginação")
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(ProdutoDTO::new);
        return ResponseEntity.ok().body(listDto);
    }


}
