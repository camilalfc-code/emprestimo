package com.projeto.AtividadeSpring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tarefa> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa cadastrar(@RequestBody Tarefa tarefa) {
        return service.cadastrar(tarefa);
    }
}

/*
@GetMapping atende GET /tarefas e devolve a lista.
@PostMapping atende POST /tarefas.
@ResponseStatus(HttpStatus.CREATED) faz o POST voltar com 201, como a professora pediu.
@RequestBody transforma o JSON que chegou em objeto Tarefa.
 */
