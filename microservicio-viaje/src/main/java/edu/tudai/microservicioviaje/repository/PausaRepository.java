package edu.tudai.microservicioviaje.repository;

import edu.tudai.microservicioviaje.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PausaRepository extends JpaRepository<Pausa, Long> {
}
