package com.diogodga.diogodga.services;
import java.util.List;

import com.diogodga.diogodga.domain.Cidade;
import com.diogodga.diogodga.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
