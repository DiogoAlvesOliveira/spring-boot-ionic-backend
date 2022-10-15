package com.diogodga.diogodga.resources;

import com.diogodga.diogodga.domain.Categoria;
import com.diogodga.diogodga.domain.Pedido;
import com.diogodga.diogodga.dto.CategoriaDTO;
import com.diogodga.diogodga.services.PedidoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;
    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca por id")
    public ResponseEntity<Pedido> find(@PathVariable Integer id){
        Pedido obj = pedidoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation(value="Insere pedido")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
        obj = pedidoService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value="Retorna todos peidos com paginação")
    @GetMapping()
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="instante") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction) {
        Page<Pedido> list = pedidoService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
