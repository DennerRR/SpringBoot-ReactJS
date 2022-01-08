package com.denner.minhasfinancas.api.resource;


import com.denner.minhasfinancas.api.DTO.LancamentoDTO;
import com.denner.minhasfinancas.service.LancamentoService;
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

    public LancamentoResource(LancamentoService service){
        this.service = service;
    }

    @PostMapping
    @ApiOperation(value = "Salvar um lancamento")
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto){

    }
}
