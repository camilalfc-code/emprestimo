package com.projeto.AtividadeSpring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recados")
public class RecadoController {

    private final RecadoService service;

    public RecadoController(RecadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recado> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recado cadastrar(@RequestBody Recado recado) {
        return service.cadastrar(recado);
    }




    /*
    @RestController avisa ao Spring: “essa classe recebe pedidos web e devolve JSON”.
    @RequestMapping("/recados") é o endereço do prédio (lembra da Pergunta 11?):
    tudo que começar com /recados cai aqui.

    O campo service guarda o chef que o Controller vai usar.
    O construtor recebe o RecadoService e guarda no campo. Quem entrega é o Spring, sozinho.
    Não precisa de import, porque o RecadoService está no mesmo pacote.

    @PostMapping diz: “esse metodo atende pedidos do tipo POST” (cadastrar = criar = POST, lembra?).
    @ResponseStatus(HttpStatus.CREATED) faz a resposta voltar com status 201 Created, o “criei!” da Pergunta 3.
    @RequestBody Recado recado pega o JSON que chegou do Postman e transforma em objeto Recado
    service.cadastrar(recado) repassa o objeto pro chef.

     */
}
