package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.model.Funcionario;
import br.com.alura.spring.data.model.FuncionarioProjecaoInterface;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>, 
																JpaSpecificationExecutor<Funcionario>{

	//Derivated Query - Queries com "by"
	public Funcionario findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate dataContrat);
	
	//Derivated Query com filtro de pesquisa
	public Funcionario findByNome(String nome);

	//JPQL
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario > :salario AND f.dataContratacao = :dataContrat")
	public Funcionario buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(String nome, BigDecimal salario, LocalDate dataContrat);
	
	//Native query
	@Query(value = "SELECT * from funcionarios where data_contratacao > :dataContrat", nativeQuery = true)
	public Funcionario nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate dataContrat);
	
	//Projection: entidade contendo apenas alguns atributos
	//Utilizado para realizar relatórios
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecaoInterface> buscarFuncionarioESalarioParaRelatorio();

}
