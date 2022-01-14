package com.diogodga.diogodga.services;

import com.diogodga.diogodga.domain.Cidade;
import com.diogodga.diogodga.domain.Cliente;
import com.diogodga.diogodga.domain.Endereco;
import com.diogodga.diogodga.domain.enums.TipoCliente;
import com.diogodga.diogodga.dto.ClienteDTO;
import com.diogodga.diogodga.dto.ClienteNewDTO;
import com.diogodga.diogodga.repositories.ClienteRepository;
import com.diogodga.diogodga.repositories.EnderecoRepository;
import com.diogodga.diogodga.services.exceptions.DataIntegrityException;
import com.diogodga.diogodga.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente find(Integer id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return clienteRepository.save(newObj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id ){
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir um cliente que possui pedido");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }
    public Cliente fromDTO(ClienteNewDTO objDto){
        Cliente cli = new Cliente(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null,null);
        Endereco end =new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli);
        end.setCidade(cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if( objDto.getTelefone2() != null){
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if( objDto.getTelefone3() != null){
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }
}
