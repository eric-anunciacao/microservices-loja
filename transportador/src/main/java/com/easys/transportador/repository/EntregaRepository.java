package com.easys.transportador.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easys.transportador.model.Entrega;

@Repository
public interface EntregaRepository extends CrudRepository<Entrega, Long> {

}
