package edu.tudai.microserviciomonopatin.repository;

import edu.tudai.microserviciomonopatin.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
}
