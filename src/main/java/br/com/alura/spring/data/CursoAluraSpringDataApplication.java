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
	@Transactional
	public void run(String... args) throws Exception {
		
		int opcao = 2;
		
		switch (opcao) {
			case 1: {
				
				//Teste Cargos
				adicionarCargos();
				atualizarCargo(1L, "ALTERADO");
				deletarCargoPorID(1L);
				listarTodosCargosSemTrazerFuncionarios();
				break;
				
			}
			case 2: {
				
				//Teste Funcionarios
				adicionarFuncionariosComCargoEUnidade();
				//listarFuncionariosComUnidades();
				listarFuncionariosPaginado(1); //pg desejada
				//buscarFuncionarioPorNome("Lucas");
				//findByNomeAndSalarioGreaterThanAndDataContratacao("Lucas", new BigDecimal(5000), LocalDate.now());
				//buscarFuncionarioPorNomeSalarioMaiorEDataContratacao("Lucas", new BigDecimal(5000), LocalDate.now());
				//nativeQueryBuscarFuncionarioPorDataContratMaior(LocalDate.now().minusDays(1L));
				
				break;
				
			}
			case 3: {
				
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
	private void listarFuncionariosPaginado(int pgDesejada) {
		
		Page<Funcionario> paginaFuncionarios = this.funcionarioService.listarFuncionariosPaginado(pgDesejada);
		
		paginaFuncionarios.forEach(funcionario -> {
			System.out.println("Nome: " + funcionario.getNome());
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
