package com.projeto.AtividadeSpring;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecadoService {
    private final RecadoRepository repository;

    public RecadoService(RecadoRepository repository) {
        this.repository = repository;
    }

    public List<Recado> listar() {
        return repository.findAll();
    }

    public Recado cadastrar(Recado recado) {
        return repository.save(recado);
    }


    /*
    O construtor recebe um RecadoRepository de fora.
    O this.repository = repository; guarda o que chegou no campo da classe.
    Quem entrega esse repository pro construtor é o Spring, sozinho, quando o projeto liga.
    Você nunca escreve new RecadoService(...) na mão.

    List<Recado> é o tipo da resposta: uma lista de recados (o GET /recados devolve vários).
    repository.findAll() é um dos métodos que o Spring gera de graça, lembra? Ele traz todas as linhas da tabela.

    Recado cadastrar(Recado recado) recebe um recado (o objeto que o Controller montou a partir do JSON) e
    devolve um Recado.
    repository.save(recado) é o save que o Spring gera de graça: ele insere uma linha nova na tabela RECADO.
    O return devolve o recado já salvo, com o id preenchido pelo banco.
    É por isso que o Postman vai mostrar o recado com id: 1 na resposta.
     */
}
