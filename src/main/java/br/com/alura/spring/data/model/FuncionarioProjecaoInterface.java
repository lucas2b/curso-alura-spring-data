package br.com.alura.spring.data.model;

import java.math.BigDecimal;

public interface FuncionarioProjecaoInterface {
	
	//Definição de métodos com prefixo "get" e 
	//nomes de atributos vindo da entidade "Funcionario"
	
	Long getId();
	String getNome();
	BigDecimal getSalario();
	
}