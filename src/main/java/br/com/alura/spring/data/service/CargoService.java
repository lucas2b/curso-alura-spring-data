package br.com.alura.spring.data.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.model.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CargoService {
	
	private final CargoRepository cargoRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	public CargoService(CargoRepository rep) {
		this.cargoRepository = rep;
	}
	
	public Cargo salvarCargo(Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	
	public void atualizarCargo(Cargo cargo) {
		cargoRepository.save(cargo);
	}
	
	public Optional<Cargo> localizarCargoPorID(Long id) {
		return cargoRepository.findById(id);
	}
	
	public List<Cargo> listarTodosCargosTrazendoFuncionarios() {
		return cargoRepository.listarTodosCargosComFuncionarios();
	}
	
	public Iterable<Cargo> listarTodosCargosSemTrazerFuncionarios(){
		return cargoRepository.findAll();
	}
	
	public void apagarPorID(Long id) {
		cargoRepository.deleteById(id);
	}
	
	public boolean consultaSessaoJPA(Cargo cargo) {
		return em.contains(cargo);
	}

}
