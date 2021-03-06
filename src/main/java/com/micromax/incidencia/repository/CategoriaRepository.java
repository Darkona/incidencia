package com.micromax.incidencia.repository;

import com.micromax.incidencia.domain.entities.incidencias.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    Iterable<Categoria> findByPadreAndHabilitado(Categoria padre, boolean activa);

    Iterable<Categoria> findByNivelAndHabilitado(int nivel, boolean activa);

    Categoria findByNombreAndHabilitado(String name, boolean activa);

    Iterable<Categoria> findByHabilitado(boolean activa);

    Categoria findById(long id);

    boolean existsCategoriaByNombreAndHabilitado(String nombre, boolean h);

    Categoria findCategoriaByNombreAndHabilitado(String nombre, boolean h);
}
