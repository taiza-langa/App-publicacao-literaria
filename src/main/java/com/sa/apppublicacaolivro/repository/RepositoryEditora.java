package com.sa.apppublicacaolivro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.apppublicacaolivro.model.Editora;

public interface RepositoryEditora extends JpaRepository<Editora, Long> {
}