package com.projeto.emprestimo.service;

import com.projeto.emprestimo.exception.ConflitoException;
import com.projeto.emprestimo.exception.NaoEncontradoException;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.repository.EmprestimoRepository;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Service
public class EquipamentoService {

        public List<Equipamento> listar(TipoEquipamento tipo, Boolean disponivel) {
        if (tipo != null && disponivel != null) {
            return repository.findByTipoAndDisponivel(tipo, disponivel);
        }
        if (tipo != null) {
            return repository.findByTipo(tipo);
        }
        if (disponivel != null) {
            return repository.findByDisponivel(disponivel);
        }
        return repository.findAll();
    }

    public Equipamento atualizar(Long id, Equipamento dados) {
        Equipamento equipamento = buscar(id);
        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());
        return repository.save(equipamento);
    }

    public Equipamento buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Equipamento não encontrado"));
    }

    public Equipamento cadastrar(Equipamento equipamento) {
        if (repository.existsByPatrimonio(equipamento.getPatrimonio())) {
            throw new ConflitoException("Patrimônio já cadastrado");
        }
        equipamento.setDisponivel(true);  // todo equipamento novo nasce disponível
        return repository.save(equipamento);
    }

    public void remover(Long id) {
        Equipamento equipamento = buscar(id);
        if (emprestimoRepository.existsByEquipamentoId(id)) {
            throw new ConflitoException("Equipamento tem histórico de empréstimos");
        }
        repository.delete(equipamento);
    }

    private final EquipamentoRepository repository;
    private final EmprestimoRepository emprestimoRepository;

    public EquipamentoService(EquipamentoRepository repository,
                              EmprestimoRepository emprestimoRepository) {
        this.repository = repository;
        this.emprestimoRepository = emprestimoRepository;
    }
}
