package com.diogodga.diogodga.repositories;

import com.diogodga.diogodga.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Enderecoepository extends JpaRepository<Endereco, Integer> {
}
