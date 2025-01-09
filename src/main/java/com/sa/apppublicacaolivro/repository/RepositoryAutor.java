package com.sa.apppublicacaolivro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.apppublicacaolivro.model.Autor;

public interface RepositoryAutor extends JpaRepository<Autor, Long> {
}