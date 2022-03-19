package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import br.com.alura.spring.data.model.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	private final FuncionarioRepository funcionarioRepository;
	
	public FuncionarioService(FuncionarioRepository func) {
		this.funcionarioRepository = func;
	}
	
	@Transactional
	public void adicionarFuncionario(Funcionario funcionario) {
		this.funcionarioRepository.save(funcionario);
	}
	
	public Iterable<Funcionario> listarFuncionarios(){
		return this.funcionarioRepository.findAll();
	}
	
	public Funcionario buscarFuncionarioPorNome(String nome) {
		return this.funcionarioRepository.findByNome(nome);
	}
	
	public Funcionario findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario,
			LocalDate dataContrat) {
		return this.funcionarioRepository.
				findByNomeAndSalarioGreaterThanAndDataContratacao(nome, salario, dataContrat);
	}
	
	public Funcionario buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(String nome, BigDecimal salario, LocalDate dataContrat) {
		return this.funcionarioRepository.
				buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(nome, salario, dataContrat);
	}
	
	public Funcionario nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate dataContrat) {
		return this.funcionarioRepository.nativeQueryBuscarFuncionarioPorDataContratMaior(dataContrat);
	}
	
	public Page<Funcionario> listarFuncionariosPaginado(int pgDesejada) {
		
		Pageable pagina = PageRequest.of(pgDesejada, 2, Sort.unsorted()); //página desejada, define o número de registros por página
		
		return this.funcionarioRepository.findAll(pagina);
	}

}
