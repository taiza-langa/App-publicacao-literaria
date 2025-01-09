package com.sa.apppublicacaolivro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.apppublicacaolivro.exception.EditoraComPublicacoesException;
import com.sa.apppublicacaolivro.model.Autor;
import com.sa.apppublicacaolivro.model.Publicacao;
import com.sa.apppublicacaolivro.repository.RepositoryAutor;

@Service
public class AutorService {

    @Autowired
    private RepositoryAutor repositoryAutor;

    @Autowired
    private PublicacaoService publicacaoService;

    public Autor salvarAutor(Autor autor) {
        return repositoryAutor.save(autor);
    }

    public List<Autor> listarAutor() {
        return repositoryAutor.findAll();
    }

    public Autor buscarPorId(Long id) {
        Optional<Autor> autor = repositoryAutor.findById(id);
        return autor.orElseThrow(() -> new RuntimeException("Autor não encontrado"));
    }

    public void deletarPorId(Long id_autor) {
        List<Publicacao> publicacoes = publicacaoService.listarPublicacoesPorAutor(id_autor);
        if (publicacoes.isEmpty()) {
            repositoryAutor.deleteById(id_autor);
        } else {
            throw new EditoraComPublicacoesException(
                    "Não é possível deletar. Existem publicações associadas a este autor.");
        }
    }
}
