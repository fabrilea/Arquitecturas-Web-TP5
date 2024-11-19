package edu.tudai.microservicioadministrador.repository;

import edu.tudai.microservicioadministrador.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    @Query("SELECT m FROM Mantenimiento m WHERE m.monopatinId = :monopatinId")
    boolean findByMonopatin(Long monopatinId);
}
