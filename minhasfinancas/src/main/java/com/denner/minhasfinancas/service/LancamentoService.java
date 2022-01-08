package com.denner.minhasfinancas.service;

import com.denner.minhasfinancas.model.entity.Lancamento;
import com.denner.minhasfinancas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);

}
