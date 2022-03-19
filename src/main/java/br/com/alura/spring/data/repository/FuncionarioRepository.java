package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.model.Funcionario;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>{
	
	public Funcionario findByNome(String nome);
	
	public Funcionario findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate dataContrat);
	
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario > :salario AND f.dataContratacao = :dataContrat")
	public Funcionario buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(String nome, BigDecimal salario, LocalDate dataContrat);
	
	@Query(value = "SELECT * from funcionarios where data_contratacao > :dataContrat", nativeQuery = true)
	public Funcionario nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate dataContrat);

}
