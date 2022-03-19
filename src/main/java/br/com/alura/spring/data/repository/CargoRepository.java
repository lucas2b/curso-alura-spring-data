package br.com.alura.spring.data.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.model.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

	@Query("SELECT c FROM Cargo c JOIN FETCH c.listaFuncionarios lf")
	List<Cargo> listarTodosCargosComFuncionarios();
}
