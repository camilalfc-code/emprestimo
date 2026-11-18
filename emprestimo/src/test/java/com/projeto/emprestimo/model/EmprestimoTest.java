package com.projeto.emprestimo.model;

import static org.junit.jupiter.api.Assertions.*;

import com.projeto.emprestimo.exception.ConflitoException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EmprestimoTest {

    private Emprestimo criarEmprestimo(LocalDate hoje) {
        return new Emprestimo(new Equipamento(), new Colaborador(), hoje);
    }

    @Test
    void criarEmprestimoDefineAsDatasDaR5() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 11, 14));

        assertEquals(LocalDate.of(2026, 11, 14), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.of(2026, 11, 28), emprestimo.getDataPrevistaDevolucao());
    }

    @Test
    void devolverEmprestimoAbertoGuardaADataDeDevolucao() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 11, 14));

        emprestimo.devolver(LocalDate.of(2026, 11, 20));

        assertEquals(LocalDate.of(2026, 11, 20), emprestimo.getDataDevolucao());
    }

    @Test
    void naoDevolveEmprestimoJaDevolvido() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 11, 14));
        emprestimo.devolver(LocalDate.of(2026, 11, 20));

        assertThrows(ConflitoException.class,
                () -> emprestimo.devolver(LocalDate.of(2026, 11, 21)));
    }

    @Test
    void emprestimoNaoDevolvidoComPrazoVencidoEstaAtrasado() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 10, 27));

        assertTrue(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void emprestimoComPrazoNoFuturoNaoEstaAtrasado() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 11, 6));

        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void emprestimoDevolvidoNaoEstaAtrasadoMesmoComPrazoVencido() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 10, 27));
        emprestimo.devolver(LocalDate.of(2026, 11, 12));

        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void noDiaDoPrazoAindaNaoEstaAtrasado() {
        Emprestimo emprestimo = criarEmprestimo(LocalDate.of(2026, 10, 31));

        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }
}