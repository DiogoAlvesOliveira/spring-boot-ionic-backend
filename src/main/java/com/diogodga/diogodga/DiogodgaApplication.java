package com.diogodga.diogodga;

import com.diogodga.diogodga.domain.*;
import com.diogodga.diogodga.domain.enums.TipoCliente;
import com.diogodga.diogodga.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DiogodgaApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private Enderecoepository enderecoepository;

	public static void main(String[] args) {
		SpringApplication.run(DiogodgaApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);

		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		Estado est1 = new Estado(null, "Pernambuco");
		Estado est2 = new Estado(null, "Ceara");

		Cidade c1 = new Cidade(null, "Recife", est1);
		Cidade c2 = new Cidade(null, "Fortaleza", est2);
		Cidade c3 = new Cidade(null, "Olinda", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().add(c2);

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678912", TipoCliente.PESSOAFISICA);
		Cliente cli2 = new Cliente(null, "Jose Silva", "jose@gmail.com", "12345678912", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("555555555","66666666666"));
		cli2.getTelefones().addAll(Arrays.asList("555555555","66666666666"));

		Endereco e1 = new Endereco(null, "Rua  Flores", "300", "casa 1", "Jardim", "38220834", cli1);
		Endereco e2 = new Endereco(null, "Rua  Rosas", "200", "casa 2", "Floresta", "645765656", cli2);

		e1.setCidade(c1);
		e2.setCidade(c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoepository.saveAll(Arrays.asList(e1, e2));
	}
}
