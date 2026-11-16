package com.projeto.AtividadeSpring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecadoRepository extends JpaRepository<Recado, Long> {
/*
JpaRepository é quem traz de graça o save, o findAll, o delete
Recado diz “esse repository cuida da entidade Recado”
Long é o tipo do id dela (lá no Recado, o id é Long)
 */


}

