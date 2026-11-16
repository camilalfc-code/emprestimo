package com.projeto.emprestimo.service;

import com.projeto.emprestimo.model.Emprestimo;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.repository.ColaboradorRepository;
import com.projeto.emprestimo.repository.EmprestimoRepository;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             EquipamentoRepository equipamentoRepository,
                             ColaboradorRepository colaboradorRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public List<Emprestimo> listar(Boolean atrasados) {
        if (Boolean.TRUE.equals(atrasados)) {
            return emprestimoRepository
                    .findByDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(LocalDate.now());
        }
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscar(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
    }

    public List<Emprestimo> listarDoColaborador(Long colaboradorId) {
        return emprestimoRepository.findByColaboradorId(colaboradorId);
    }

    /*
    listar: o filtro ?atrasados=true é opcional, então chega como true, false ou null.
    Por isso o Boolean.TRUE.equals(atrasados): só entra no if se veio exatamente true,
    e o null não dá erro. É a R8.
    buscar: igual ao do equipamento, com 404 se o id não existir.
    listarDoColaborador: devolve o histórico da pessoa.
     */

    @Transactional
    public Emprestimo emprestar(Long equipamentoId, Long colaboradorId) {
        Equipamento equipamento = equipamentoRepository.findById(equipamentoId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        if (!colaboradorRepository.existsById(colaboradorId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Colaborador não encontrado");
        }

        LocalDate hoje = LocalDate.now();

        if (emprestimoRepository
                .existsByColaboradorIdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(colaboradorId, hoje)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Colaborador está com um empréstimo atrasado");
        }

        equipamento.emprestar();
        equipamentoRepository.save(equipamento);

        return emprestimoRepository.save(new Emprestimo(equipamentoId, colaboradorId, hoje));
    }

    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = buscar(id);

        emprestimo.devolver(LocalDate.now());

        Equipamento equipamento = equipamentoRepository.findById(emprestimo.getEquipamentoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Equipamento não encontrado"));
        equipamento.devolver();
        equipamentoRepository.save(equipamento);

        return emprestimoRepository.save(emprestimo);
    }

}
