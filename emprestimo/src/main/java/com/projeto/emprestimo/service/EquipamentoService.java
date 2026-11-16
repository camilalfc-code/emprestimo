package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.repository.EmprestimoRepository;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    // Metodo responsável por buscar um equipamento pelo ID.
    public Equipamento buscar(Long id) {

        // Procura no banco um equipamento com o ID informado.
        // orElseThrow: se não encontrar, lança uma exceção.
        return repository.findById(id).orElseThrow(() ->

                // Retorna o erro HTTP 404 (não encontrado),
                // acompanhado da mensagem.
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipamento não encontrado"
                )
        );
    }

    public Equipamento cadastrar(Equipamento equipamento) {
        if (repository.existsByPatrimonio(equipamento.getPatrimonio())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Patrimônio já cadastrado");
        }
        equipamento.setDisponivel(true);  // todo equipamento novo nasce disponível
        return repository.save(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento dados) {
        Equipamento equipamento = buscar(id);
        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());
        return repository.save(equipamento);
    }

      public void remover(Long id) {
        Equipamento equipamento = buscar(id);
        if (emprestimoRepository.existsByEquipamentoId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Equipamento tem histórico de empréstimos");
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
