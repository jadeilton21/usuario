package com.jadenauta.usuario.infra.repository;


import com.javanauta.aprendendoSpringBoot.infra.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
