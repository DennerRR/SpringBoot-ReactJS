package com.denner.minhasfinancas.api.resource;


import com.denner.minhasfinancas.api.DTO.AtualizarStatusDTO;
import com.denner.minhasfinancas.api.DTO.LancamentoDTO;
import com.denner.minhasfinancas.exception.RegraNegocioException;
import com.denner.minhasfinancas.model.entity.Lancamento;
import com.denner.minhasfinancas.model.entity.Usuario;
import com.denner.minhasfinancas.model.enums.StatusLancamento;
import com.denner.minhasfinancas.model.enums.TipoLancamento;
import com.denner.minhasfinancas.service.LancamentoService;
import com.denner.minhasfinancas.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@Api(value = "API Rest Lancamentos")

//Liberar todos domínios para acessar essa API
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LancamentoResource {

    private final LancamentoService service;
    private final UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity buscar(@RequestParam (value = "descricao", required = false) String descricao,
                                 @RequestParam (value = "mes", required = false) Integer mes,
                                 @RequestParam(value = "ano", required = false) Integer ano,
                                 @RequestParam("usuario")Long idUsuario){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if(!usuario.isPresent()){
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado.");
        }else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);

    }

    @PostMapping("/salvar")
    @ApiOperation(value = "Salvar um lancamento")
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto){
        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @PutMapping("/atualizarStatus/{id}")
    @ApiOperation(value = "Atualizar o status de um lançamento.")
    public ResponseEntity atualizarStatus(@RequestBody AtualizarStatusDTO dto, @PathVariable("id") Long id){
        return service.obterPorId(id).map(entity ->{
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
            if(statusSelecionado == null){
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envia um status válido.");
            }
            try {
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.ok(entity);
            }catch(RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            }).orElseGet(() ->
                new ResponseEntity("Lançamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }


    @PutMapping("/atualizar/{id}")
    @ApiOperation(value = "Atualizar um lancamento")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto){
        return service.obterPorId(id).map(entity -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            }catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());

            }
            }).orElseGet(() -> new ResponseEntity ("Lancamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/deletar/{id}")
    @ApiOperation(value = "deletar um lancamento por id")
    public ResponseEntity deletar(@PathVariable("id") Long id){
        return service.obterPorId(id).map(entidade ->{
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService.obterPorId(dto.getUsuario()).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o id informado"));

        lancamento.setUsuario(usuario);
        if(dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }
        if(dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }
}
