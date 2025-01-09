// atividade
package com.sa.apppublicacaolivro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sa.apppublicacaolivro.model.Publicacao;

public interface RepositoryPuplicacao extends JpaRepository<Publicacao, Long> {

    @Query("SELECT p FROM Publicacao p WHERE p.editora.idEditora = :id_editora")
    List<Publicacao> findByEditoraId(@Param("id_editora") Long idEditora);

    @Query("SELECT p FROM Publicacao p WHERE p.autor.id_autor = :id_autor")
    List<Publicacao> findByAutorId(@Param("id_autor") Long idAutor);

    @Query("SELECT p FROM Publicacao p WHERE p.autor.id_autor = :id_autor AND p.editora.idEditora = :id_editora")
    List<Publicacao> findByAutorIdAndEditoraId(@Param("id_autor") Long idAutor, @Param("id_editora") Long idEditora);

}
