package com.micromax.incidencia.viewmodel;

import com.micromax.incidencia.domain.entities.incidencias.TipoIncidencia;
import lombok.Data;

@Data
public class TipoIncidenciaViewmodel {
    private TipoIncidencia tipoIncidencia;
    private String message;
}
