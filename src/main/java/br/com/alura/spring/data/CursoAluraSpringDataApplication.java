package br.com.alura.spring.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import br.com.alura.spring.data.model.Cargo;
import br.com.alura.spring.data.model.Funcionario;
import br.com.alura.spring.data.model.FuncionarioProjecaoInterface;
import br.com.alura.spring.data.model.UnidadeDeTrabalho;
import br.com.alura.spring.data.service.CargoService;
import br.com.alura.spring.data.service.FuncionarioService;

@SpringBootApplication
@EnableTransactionManagement
@SuppressWarnings("unused")
public class CursoAluraSpringDataApplication implements CommandLineRunner{
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private FuncionarioService funcionarioService;

	public static void main(String[] args) {
		SpringApplication.run(CursoAluraSpringDataApplication.class, args);
	}

	@Override
	@Transactional //para adicionar lista de Funcionários
	public void run(String... args) throws Exception {
		
		int opcao = 2;
		
		switch (opcao) {
			case 1: { 
				//Teste Cargos
				
				//Utilização de: "Save" do Spring Data
				adicionarCargos(); 
				
				//Utilização de: "Save" do Spring Data
				atualizarCargo(1L, "ALTERADO");
				
				//Utilização de: "deleteById" do Spring Data
				deletarCargoPorID(1L);
				
				//Utilização de: findAll do Spring Data
				listarTodosCargosSemTrazerFuncionarios();
				
				break;
				
			}
			case 2: { 
				//Teste Funcionarios
				
				//Utilização de: "Save" do Spring Data
				adicionarFuncionariosComCargoEUnidade();
				
				//Utilização de: "findAll" (Unidade é EAGER)
				listarFuncionariosComUnidades(); 
				
				//Utilização de: Paginação utilizando "findAll" com parâmetro "Sort"
//				listarFuncionariosPaginadosOrdenadosPorNome(0); //pg desejada
//				listarFuncionariosPaginadosOrdenadosPorNome(1); //pg desejada
//				listarFuncionariosPaginadosOrdenadosPorNome(2); //pg desejada
//				listarFuncionariosPaginadosOrdenadosPorNome(3); //pg desejada
//				listarFuncionariosPaginadosOrdenadosPorNome(4); //pg desejada
				
				//Utilização de: Derivated Query com "by"
				buscarFuncionarioPorNome("Lucas"); 
				
				//Utilização de: Derivated Query com composição de critérios
				findByNomeAndSalarioGreaterThanAndDataContratacao("Lucas", new BigDecimal(5000), LocalDate.now()); 
				
				//Utilização de: JPQL
				buscarFuncionarioPorNomeSalarioMaiorEDataContratacao("Lucas", new BigDecimal(5000), LocalDate.now());
				
				//Utilização de: Native Query que retorna tabela que corresponde a UMA entidade
				nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate.now().minusDays(1L));
				
				//Utilização de: Projection (entidade contendo atributos selecionados para um relatório)
				buscarFuncionarioESalarioParaRelatorio();
				
				//Utilização de: Specification
				buscaFuncionarioComNomeDinamico("Lucas");
				
				break;
			}
			default:
		}
	}
	
	//------------------ Funções de Funcionários --------------------
	
	private void listarFuncionariosComUnidades() {
		
		this.funcionarioService.listarFuncionarios().forEach(funcionario -> {
			
			System.out.println("Nome: " + funcionario.getNome());
			
			if(!funcionario.getListaUnidadesDeTrabalho().isEmpty()) {
				System.out.println("Unidades de trabalho do funcionario " + funcionario.getNome());
				funcionario.getListaUnidadesDeTrabalho().forEach(unidadeTrabalho -> {
					System.out.println("Unidade: " + unidadeTrabalho.getDescricao());
				});
			}
		});
		
	}

	private void adicionarFuncionariosComCargoEUnidade() {
		
		Cargo cargoProgramador = new Cargo();
		cargoProgramador.setDescricao("Programador");
		
		Cargo cargoVendedor = new Cargo();
		cargoVendedor.setDescricao("Vendedor");
		
		UnidadeDeTrabalho unidade1 = new UnidadeDeTrabalho("Unidade 1", "Endereco 1");
		UnidadeDeTrabalho unidade2 = new UnidadeDeTrabalho("Unidade 2", "Endereco 2");
		UnidadeDeTrabalho unidade3 = new UnidadeDeTrabalho("Unidade 3", "Endereco 3");
		List<UnidadeDeTrabalho> listaUnidadesDeTrabalho = new ArrayList<>();
		listaUnidadesDeTrabalho.add(unidade1);
		listaUnidadesDeTrabalho.add(unidade2);
		listaUnidadesDeTrabalho.add(unidade3);

		this.funcionarioService.adicionarFuncionario(new Funcionario("Lucas", "01127022016", new BigDecimal(8000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho)); 
		
		System.out.println("Transação 1:" + TransactionSynchronizationManager.getCurrentTransactionName());

		this.funcionarioService.adicionarFuncionario(new Funcionario("Paulo", "02418823453", new BigDecimal(5000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho)); 

		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Celso", "01127022016", new BigDecimal(8000), 
				LocalDate.now(), cargoVendedor, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("João", "13412341", new BigDecimal(3000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("José", "055123125", new BigDecimal(2000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Ana", "561278719872", new BigDecimal(4000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Marcos", "5313413453", new BigDecimal(3000), 
				LocalDate.now(), cargoProgramador, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Maria", "6313413423453", new BigDecimal(4500), 
				LocalDate.now(), cargoVendedor, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Claudia", "43412313453", new BigDecimal(3300), 
				LocalDate.now(), cargoVendedor, listaUnidadesDeTrabalho));
		
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Pedro", "83134512513453", new BigDecimal(2100), 
				LocalDate.now(), cargoVendedor, listaUnidadesDeTrabalho));

	
		this.funcionarioService.
		adicionarFuncionario(new Funcionario("Adão", "9135124413453", new BigDecimal(5600), 
				LocalDate.now(), cargoVendedor, listaUnidadesDeTrabalho));
	}
	
	private void buscarFuncionarioPorNome(String nome) {
		Funcionario funcionario = this.funcionarioService.buscarFuncionarioPorNome(nome);
		System.out.println("Funcionario encontrado: " + funcionario.getNome());
	}
	
	private void findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario,
			LocalDate dataContrat) { 
		
		Funcionario funcionario = this.funcionarioService.findByNomeAndSalarioGreaterThanAndDataContratacao(nome,
				salario, dataContrat);
		System.out.println("Funcionario encontrado: " + funcionario.getNome());
	}
	
	private void buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(String nome, BigDecimal salario,
			LocalDate dataContrat) {
		
		Funcionario funcionario = this.funcionarioService.buscarFuncionarioPorNomeSalarioMaiorEDataContratacao(nome,
				salario, dataContrat);
		
		System.out.println("Funcionario encontrado: " + funcionario.getNome());	
	}
	
	private void nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate dataContrat) {
		Funcionario funcionario = this.funcionarioService.nativeQueryBuscarFuncionarioPorDataContratMaior(dataContrat);
		
		System.out.println("Funcionario encontrado: " + funcionario.getNome());	
	}
	
	//retorna uma página contendo 2 registros
	private void listarFuncionariosPaginadosOrdenadosPorNome(int pgDesejada) {
		
		Page<Funcionario> paginaFuncionarios = this.funcionarioService.listarFuncionariosPaginadosOrdenadosPorNome(pgDesejada);
		
		paginaFuncionarios.forEach(funcionario -> {
			System.out.println("Nome: " + funcionario.getNome());
		});
		
	}
	
	private void buscarFuncionarioESalarioParaRelatorio() {
		List<FuncionarioProjecaoInterface> listaFuncionarioRelatorio = this.
				funcionarioService.buscarFuncionarioESalarioParaRelatorio();
	
		listaFuncionarioRelatorio.forEach(funcionario -> {
			System.out.println("ID :" + funcionario.getId());
			System.out.println("Nome: " + funcionario.getNome());
			System.out.println("Salário: " + funcionario.getSalario());
			System.out.println();
		});
	}
	
	private void buscaFuncionarioComNomeDinamico(String nome) {
		List<Funcionario> listaFuncionarios =  this.funcionarioService.
													buscaFuncionarioComNomeDinamico(nome);
		
		listaFuncionarios.forEach(funcionario -> {
			System.out.println("ID :" + funcionario.getId());
			System.out.println("Nome: " + funcionario.getNome());
			System.out.println("Salário: " + funcionario.getSalario());
			System.out.println();
		});
	
	}
	
	//------------------ Funções de Cargos --------------------
	
	private void adicionarCargos() {
		Cargo cargo = new Cargo();
		cargo.setDescricao("Programador");
		cargoService.salvarCargo(cargo); //salva 1 cargo
		
		cargo = new Cargo();
		cargo.setDescricao("Tester");
		cargoService.salvarCargo(cargo); //salva mais 1 cargo
	}
	
	private void atualizarCargo(Long id, String novaDescricao) {
		Optional<Cargo> cargoId1 = cargoService.localizarCargoPorID(id);
		cargoId1.get().setDescricao(novaDescricao);
		
		cargoService.atualizarCargo(cargoId1.get()); //atualiza o primeiro cargo
	}
	
	private void listarTodosCargosSemTrazerFuncionarios() {
		
		System.out.println("Listando todos cargos: ");
		cargoService.listarTodosCargosSemTrazerFuncionarios().forEach(cargo -> {
			System.out.println("[Id: " + cargo.getId() + ", Descrição:" + cargo.getDescricao() + "]");
			
		});
	}
	
	private void listarTodosCargosTrazendoFuncionarios() {
		
		System.out.println("Listando todos cargos: ");
		cargoService.listarTodosCargosTrazendoFuncionarios().forEach(cargo -> {
			System.out.println("[Id: " + cargo.getId() + ", Descrição:" + cargo.getDescricao() + "]");
			
			//Testa se lista de funcionarios não é vazia
			if(!cargo.getListaFuncionarios().isEmpty()) {				
				System.out.println("Funcionarios com este cargo:");
			
				cargo.getListaFuncionarios().forEach(funcionario -> {
					System.out.println("Nome: " + funcionario.getNome());
				});
			
			}
			
		});
	}
	
	private void deletarCargoPorID(Long id) {
		cargoService.apagarPorID(id);
	}
}