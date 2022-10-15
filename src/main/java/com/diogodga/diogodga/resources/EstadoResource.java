package com.diogodga.diogodga.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.diogodga.diogodga.domain.Cidade;
import com.diogodga.diogodga.domain.Estado;
import com.diogodga.diogodga.dto.CidadeDTO;
import com.diogodga.diogodga.dto.EstadoDTO;
import com.diogodga.diogodga.services.CidadeService;
import com.diogodga.diogodga.services.EstadoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @ApiOperation(value="Retorna todos estados")
    @GetMapping()
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = service.findAll();
        List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @ApiOperation(value="Retorna todas cidades do estado")
    @GetMapping(value="/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
