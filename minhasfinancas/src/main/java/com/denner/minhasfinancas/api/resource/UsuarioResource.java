package com.denner.minhasfinancas.api.resource;

import com.denner.minhasfinancas.api.DTO.UsuarioDTO;
import com.denner.minhasfinancas.exception.ErroAutenticacao;
import com.denner.minhasfinancas.exception.RegraNegocioException;
import com.denner.minhasfinancas.model.entity.Usuario;
import com.denner.minhasfinancas.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Api(value = "API Rest Usuarios")

//Liberar todos domínios para acessar essa API
@CrossOrigin(origins = "*")
public class UsuarioResource {


    private UsuarioService service;

    public UsuarioResource(UsuarioService service){
        this.service = service;

    }
    @PostMapping("/salvar")
    @ApiOperation(value = "Salvar um usuario")
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto){
        Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

        try{
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());


        }

    }
    @PostMapping("/autenticar")
    @ApiOperation(value = "Autenticar usuario")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch(ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
