package com.diogodga.diogodga.resources;

import com.diogodga.diogodga.domain.Categoria;
import com.diogodga.diogodga.dto.CategoriaDTO;
import com.diogodga.diogodga.services.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
@Api( tags = "Categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @ApiOperation(value="Retorna todas categorias")
    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = categoriaService.findAll();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca por id")
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
        Categoria obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Insere categoria")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = categoriaService.fromDTO(objDto);
        obj = categoriaService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Atualiza categoria")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Categoria> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
        Categoria obj = categoriaService.fromDTO(objDto);
        obj.setId(id);
        obj = categoriaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Remove categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
            @ApiResponse(code = 404, message = "Código inexistente") })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Categoria> delete (@PathVariable Integer id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Retorna todas categorias com paginação")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Categoria> list = categoriaService.findPage(page,linesPerPage, orderBy,direction);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }
}
