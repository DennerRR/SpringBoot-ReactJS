package com.denner.minhasfinancas.api.resource;


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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lancamentos")
@Api(value = "API Rest Lancamentos")

//Liberar todos domínios para acessar essa API
@CrossOrigin(origins = "*")
public class LancamentoResource {

    private LancamentoService service;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService service){
        this.service = service;
    }

    @PostMapping
    @ApiOperation(value = "Salvar um lancamento")
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto){
        return null;
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
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;
    }
}
