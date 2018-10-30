package com.micromax.incidencia.service.impl;

import com.micromax.incidencia.config.ConfiguracionGeneral;
import com.micromax.incidencia.domain.Constants;
import com.micromax.incidencia.domain.entities.incidencias.Incidencia;
import com.micromax.incidencia.domain.entities.users.Tecnico;
import com.micromax.incidencia.service.EstrategiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstrategiaServiceImpl implements EstrategiaService {

    @Autowired
    private ConfiguracionGeneral config;

    public Tecnico ejecutarEstrategia(Incidencia incidencia, List<Tecnico> tecnicos){

        if(config.getTipoEstrategia().equalsIgnoreCase(Constants.AUTO)){
            List<Tecnico> disponibles = new ArrayList<>();

            for (Tecnico tecnico : tecnicos) {
                if (tecnico.getCategoriasTecnico().contains(incidencia.getCategoria())) {
                    disponibles.add(tecnico);
                }
            }

            Tecnico elegido = encontrarTecnicoMasDisponible(disponibles);


            incidencia.getAsignados().add(elegido);
            return elegido;
        }
        return null;
    }

    public Tecnico encontrarTecnicoMasDisponible(List<Tecnico> tecs){
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < tecs.size(); i++){
            int size = tecs.get(i).getIncidencias().size();
            if(tecs.get(i).getIncidencias().size()< min) {
                min = size;
                index = i;
            }
        }
        return tecs.get(index);
    }
}