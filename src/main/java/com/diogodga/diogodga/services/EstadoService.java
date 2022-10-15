package com.diogodga.diogodga.services;

import java.util.List;

import com.diogodga.diogodga.domain.Estado;
import com.diogodga.diogodga.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderByNome();
    }
}
