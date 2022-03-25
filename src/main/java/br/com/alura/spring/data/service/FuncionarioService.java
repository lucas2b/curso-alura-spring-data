package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import br.com.alura.spring.data.model.Funcionario;
import br.com.alura.spring.data.model.FuncionarioProjecaoInterface;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.FuncionarioSpecification;

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
	
	public Page<Funcionario> listarFuncionariosPaginadosOrdenadosPorNome(int pgDesejada) {
		
		Pageable pagina = PageRequest.of(pgDesejada, 2, Sort.by(Sort.Direction.ASC, "nome")); 
										//página desejada, define o número de registros por página, ordenação
		
		return this.funcionarioRepository.findAll(pagina);
	}
	
	public List<FuncionarioProjecaoInterface> buscarFuncionarioESalarioParaRelatorio() {
		return this.funcionarioRepository.buscarFuncionarioESalarioParaRelatorio();
	}
	
	//Busca funcionário com nome dinamicamente, caso passe o nome realiza o filtro
	//Caso não passe o nome, traz todos funcionários
	public List<Funcionario> buscaFuncionarioComNomeDinamico(String nome) {
		
		return this.funcionarioRepository.
				findAll(Specification.where(FuncionarioSpecification.nome(nome)));
	}

	
	
}
