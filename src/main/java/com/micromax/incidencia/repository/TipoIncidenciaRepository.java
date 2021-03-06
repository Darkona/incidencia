package com.micromax.incidencia.repository;

import com.micromax.incidencia.domain.entities.incidencias.TipoIncidencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TipoIncidenciaRepository extends CrudRepository<TipoIncidencia,Long> {

    Iterable<TipoIncidencia> findAllByIdAndHabilitado(long id, boolean h);

    Optional<TipoIncidencia> findById(long id);

    boolean existsByNombre(String nombre);
}
