package com.denner.minhasfinancas.service.impl;

import com.denner.minhasfinancas.model.entity.Usuario;
import com.denner.minhasfinancas.model.repository.UsuarioRepository;
import com.denner.minhasfinancas.service.UsuarioService;

public class UsuarioServiceImp implements UsuarioService {

    private UsuarioRepository repository;


    public UsuarioServiceImp(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {

    }
}
