package edu.tudai.microserviciomonopatin.repository;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    // Contar monopatines disponibles y no en mantenimiento
    @Query("SELECT COUNT(m) FROM Monopatin m WHERE m.disponible = true AND m.enMantenimiento = false")
    long countByDisponibleTrueAndEnMantenimientoFalse();

    // Contar monopatines en mantenimiento
    @Query("SELECT COUNT(m) FROM Monopatin m WHERE m.enMantenimiento = true")
    long countByEnMantenimientoTrue();


    @Query("SELECT m FROM Monopatin m WHERE " +
            "(6371 * acos(cos(radians(:latitud)) * cos(radians(m.latitud)) * " +
            "cos(radians(m.longitud) - radians(:longitud)) + sin(radians(:latitud)) * " +
            "sin(radians(m.latitud)))) < :radio")
    List<Monopatin> findMonopatinesCercanos(@Param("latitud") double latitud,
                                            @Param("longitud") double longitud,
                                            @Param("radio") double radio);

    @Query("SELECT m from Monopatin m WHERE m.kilometrosRecorridos <= :km")
    List<Monopatin> findMonopatinesKilometros(Double km);
}
