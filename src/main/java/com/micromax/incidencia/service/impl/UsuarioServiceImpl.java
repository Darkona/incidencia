package com.micromax.incidencia.service.impl;

import com.micromax.incidencia.domain.entities.users.Cliente;
import com.micromax.incidencia.domain.entities.users.Rol;
import com.micromax.incidencia.domain.entities.users.Tecnico;
import com.micromax.incidencia.domain.entities.users.Usuario;
import com.micromax.incidencia.dto.UsuarioDTO;
import com.micromax.incidencia.repository.PrivilegioRepository;
import com.micromax.incidencia.repository.RolRepository;
import com.micromax.incidencia.repository.UsuarioRepository;
import com.micromax.incidencia.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PrivilegioRepository privilegioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmailAndHabilitado(email, true).orElse(null);
    }

    @Override
    public List<Rol> getRoles() {
        return (ArrayList<Rol>) rolRepository.findAll();
    }

    @Override
    public Collection<Usuario> getUsuariosActivos() {
        return usuarioRepository.findAllByHabilitado(true);
    }

    @Override
    public Usuario getUsuarioById(long id) {
        return usuarioRepository.findByIdUsuarioAndHabilitado(id, true);
    }

    @Override
    public boolean borrarUsuario(Long id) {
        Usuario u = usuarioRepository.findByIdUsuarioAndHabilitado(id, true);
        if(u != null) {
            u.setHabilitado(false);
            log.info("Eliminada incidencia con id %d", id);
            return usuarioRepository.save(u) != null;
        }
        return false;
    }

    @Override
    public Usuario findUsuarioByUsername(String username) {
        return usuarioRepository.findUsuarioByUsernameAndHabilitado(username, true);
    }

    @Override
    public boolean existeUsuario(String username) {
        return usuarioRepository.existsByUsernameAndHabilitado(username, true);
    }

    public void guardarUsuario(Usuario usuario, boolean bool){
        if(bool)usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public void guardarUsuario(UsuarioDTO usuarioDTO, boolean nuevo) {
        Usuario usuario;
        switch (usuarioDTO.getTipoUsuario()){
            case 1: usuario = new Usuario();
                    break;
            case 2: usuario = new Tecnico();
                    ((Tecnico) usuario).setCapacidad(usuarioDTO.getCapacidad());
                    break;
            case 3: usuario = new Cliente();
                    ((Cliente) usuario).setRif(usuarioDTO.getRif());
                    ((Cliente) usuario).setDenominacionComercial(usuarioDTO.getDenominacionComercial());
                    ((Cliente) usuario).setRazonSocial(usuarioDTO.getRazonSocial());
                    break;
            default: usuario = new Usuario();
                    break;
        }
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        if(nuevo){
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }else{
            Usuario u = usuarioRepository.findByIdUsuarioAndHabilitado(usuarioDTO.getId(),true);
            usuario.setPassword(u.getPassword());
            usuario.setIdUsuario(u.getIdUsuario());
        }

        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setRol(rolRepository.findByIdRol(usuarioDTO.getIdRol()));
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setHabilitado(true);
        usuarioRepository.save(usuario);
    }


    public Usuario getUsuarioByUsername(String username){
        return usuarioRepository.findUsuarioByUsernameAndHabilitado(username, true);
    }

}
