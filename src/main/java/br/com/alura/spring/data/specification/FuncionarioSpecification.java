package br.com.alura.spring.data.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.spring.data.model.Funcionario;

public class FuncionarioSpecification {
	
	public static Specification<Funcionario> nome(String nome){
		
		return new Specification<Funcionario>() {
			@Override
			public Predicate toPredicate(Root<Funcionario> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("nome"), nome);
			}
		};
		
//		Versão com lambda expression
//		return (root, criteriaQuery, criteriaBuilder) -> 
//			criteriaBuilder.equal(root.get("nome"),nome);
	}
	
	public static Specification<Funcionario> cpf(String cpf){
		return (root, criteriaQuery, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("cpf"), cpf);
	}
	
	public static Specification<Funcionario> salario(BigDecimal salario){
		return (root, criteriaQuery, criteriaBuilder) ->
			criteriaBuilder.greaterThan(root.get("salario"), salario);
	}

	public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao){
		return (root, criteriaQuery, criteriaBuilder) ->
			criteriaBuilder.greaterThan(root.get("dataContratacao"), dataContratacao);
	}

}
