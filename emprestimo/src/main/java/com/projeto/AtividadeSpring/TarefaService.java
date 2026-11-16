package com.projeto.AtividadeSpring;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public List<Tarefa> listar() {
        return repository.findAll();
    }

    public Tarefa cadastrar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

}

/*
listar() devolve a lista de todas as tarefas (findAll, que o Spring gera de graça).
cadastrar(Tarefa tarefa) recebe a tarefa, salva no banco (save) e devolve a tarefa já com o id preenchido.
 */
