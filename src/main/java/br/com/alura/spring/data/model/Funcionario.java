package br.com.alura.spring.data.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private BigDecimal salario;
	private LocalDate dataContratacao = LocalDate.now();
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "cargo_id")
	private Cargo cargo;
	
	@ManyToMany(cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
	private List<UnidadeDeTrabalho> listaUnidadesDeTrabalho = new ArrayList<UnidadeDeTrabalho>();
	
	public Funcionario() {
		
	}
	
	
	public Funcionario(String nome, String cpf, BigDecimal salario, LocalDate dataContratacao, Cargo cargo,
			List<UnidadeDeTrabalho> listaUnidadesDeTrabalho) {
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
		this.cargo = cargo;
		this.listaUnidadesDeTrabalho = listaUnidadesDeTrabalho;
	}



	public List<UnidadeDeTrabalho> getListaUnidadesDeTrabalho() {
		return listaUnidadesDeTrabalho;
	}

	public void setListaUnidadesDeTrabalho(List<UnidadeDeTrabalho> listaUnidadesDeTrabalho) {
		this.listaUnidadesDeTrabalho = listaUnidadesDeTrabalho;
	}

	public void adicionaUnidadeDeTrabalho(UnidadeDeTrabalho unidade) {
		listaUnidadesDeTrabalho.add(unidade);
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	public LocalDate getDataContratacao() {
		return dataContratacao;
	}


}
