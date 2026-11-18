# Sistema de Empréstimo de Equipamentos

API em Java com Spring Boot para controlar o empréstimo de equipamentos (notebooks, monitores, headsets) para colaboradores.

**API no ar (Render):** https://emprestimo-rhxj.onrender.com

> No plano grátis do Render, a primeira requisição depois de um tempo parado pode levar cerca de 1 minuto. O banco (H2) perde os dados quando o serviço reinicia.

## Como rodar

1. Java 25 e Maven instalados (ou use o IntelliJ).
2. Rode a classe `EmprestimoApplication` ou use `./mvnw spring-boot:run`.
3. A API sobe em http://localhost:8080.
4. Console do banco H2: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:file:./dados/emprestimos`, usuário `sa`, senha vazia).
5. Para rodar os testes: `./mvnw test`.

## Rotas

| Método | Rota | O que faz | Status |
|---|---|---|---|
| POST | /equipamentos | Cadastra equipamento | 201 |
| GET | /equipamentos | Lista (filtros opcionais `?tipo=` e `?disponivel=`) | 200 |
| GET | /equipamentos/{id} | Busca um equipamento | 200 |
| PUT | /equipamentos/{id} | Atualiza nome e tipo | 200 |
| DELETE | /equipamentos/{id} | Remove (se nunca foi emprestado) | 204 |
| POST | /colaboradores | Cadastra colaborador | 201 |
| GET | /colaboradores | Lista | 200 |
| GET | /colaboradores/{id} | Busca um colaborador | 200 |
| GET | /colaboradores/{id}/emprestimos | Histórico de empréstimos | 200 |
| POST | /emprestimos?equipamentoId=1&colaboradorId=2 | Empresta | 201 |
| GET | /emprestimos | Lista (com `?atrasados=true`, só os atrasados) | 200 |
| GET | /emprestimos/{id} | Busca um empréstimo | 200 |
| PUT | /emprestimos/{id}/devolucao | Registra a devolução | 200 |

## Regras de negócio

- R1: não existem dois equipamentos com o mesmo patrimônio.
- R2: todo equipamento novo nasce disponível.
- R3: não existem dois colaboradores com o mesmo e-mail.
- R4: só se empresta equipamento disponível.
- R5: ao emprestar, o equipamento fica indisponível, a data do empréstimo é hoje e a devolução prevista é daqui a 14 dias.
- R6: empréstimo já devolvido não pode ser devolvido de novo.
- R7: ao devolver, a data de devolução é hoje e o equipamento volta a ficar disponível.
- R8: empréstimo atrasado = não devolvido e com a data prevista já vencida.
- R9: equipamento que já teve empréstimo não pode ser removido.
- R10: quem está com empréstimo atrasado não pode pegar outro equipamento.

## Testes

Testes unitários com JUnit 5 em `EquipamentoTest` e `EmprestimoTest`.

## Bônus feitos

- **Swagger:** documentação das rotas em `/swagger-ui.html` (local: http://localhost:8080/swagger-ui.html, Render: https://emprestimo-rhxj.onrender.com/swagger-ui.html)
- - **Tratamento de erros:** exceções próprias (`NaoEncontradoException` → 404 e `ConflitoException` → 409) e um `@RestControllerAdvice` (`TratadorDeErros`) que devolve sempre `{"message": "..."}`.