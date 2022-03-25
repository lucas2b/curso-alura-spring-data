package br.com.alura.spring.data.specification;

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
}
