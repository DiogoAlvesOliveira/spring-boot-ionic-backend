package com.diogodga.diogodga.resources;

import com.diogodga.diogodga.domain.Categoria;
import com.diogodga.diogodga.domain.Cliente;
import com.diogodga.diogodga.dto.CategoriaDTO;
import com.diogodga.diogodga.dto.ClienteDTO;
import com.diogodga.diogodga.dto.ClienteNewDTO;
import com.diogodga.diogodga.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
@Api( tags = "Clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value="Retorna todos clientes")
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = clienteService.findAll();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca por id")
    public ResponseEntity<Cliente> find(@PathVariable Integer id){
        Cliente obj = clienteService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation(value="Atualiza cliente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
        Cliente obj = clienteService.fromDTO(objDto);
        obj.setId(id);
        obj = clienteService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Remove cliente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cliente> delete (@PathVariable Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Busca cliente por email")
    @GetMapping(value="/email")
    public ResponseEntity<Cliente> findByEmail(@RequestParam(value="value") String email) {
        Cliente obj = clienteService.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Retorna todos clientes com paginação")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Cliente> list = clienteService.findPage(page,linesPerPage, orderBy,direction);
        Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Insere cliente")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
        Cliente obj = clienteService.fromDTO(objDto);
        obj = clienteService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value="Insere imagem perfil do cliente")
    @PostMapping(value="/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
        URI uri = clienteService.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }
}
