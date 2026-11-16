package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipamentoService {

    // Guarda uma referência ao Repository,
    // que é responsável por acessar o banco de dados.
    private final EquipamentoRepository repository;

    // Construtor: recebe o Repository e guarda na variável.
    // Isso é chamado de injeção de dependência.
    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }

    // Metodo responsável por listar os equipamentos.
    // Recebe um tipo de equipamento como parâmetro.
    public List<Equipamento> listar(TipoEquipamento tipo) {

        // Verifica se o tipo recebido é nulo (não foi informado).
        if (tipo == null) {

            // Se não informou o tipo, busca todos os equipamentos.
            return repository.findAll();
        }

        // Se informou um tipo, busca apenas os equipamentos
        // que pertencem àquele tipo.
        return repository.findByTipo(tipo);
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
        EquipamentoRepository repository = null;
        if (repository.existsByPatrimonio(equipamento.getPatrimonio())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Patrimônio já cadastrado");
        }
        equipamento.setDisponivel(true);  // todo equipamento novo nasce disponível
        return repository.save(equipamento);
    }

    
}
