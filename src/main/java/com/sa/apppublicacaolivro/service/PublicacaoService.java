package com.sa.apppublicacaolivro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.apppublicacaolivro.model.Publicacao;
import com.sa.apppublicacaolivro.repository.RepositoryPuplicacao;

@Service
public class PublicacaoService {

    @Autowired
    private RepositoryPuplicacao publicacaoRepository;

    public Publicacao salvarPublicacao(Publicacao publicacao) {
        return publicacaoRepository.save(publicacao);
    }

    public List<Publicacao> listarPublicacao() {
        return publicacaoRepository.findAll();
    }

    public Publicacao buscarPorId(Long id) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(id);
        return publicacao.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deletarPorId(Long id) {
        publicacaoRepository.deleteById(id);
    }

    public List<Publicacao> listarPublicacoesPorEditora(Long id_editora) {
        return publicacaoRepository.findByEditoraId(id_editora);
    }

    public List<Publicacao> listarPublicacoesPorAutor(Long id_autor) {
        return publicacaoRepository.findByAutorId(id_autor);
    }

    public List<Publicacao> listarPublicacoesPorAutorEEditora(Long idAutor, Long idEditora) {
        return publicacaoRepository.findByAutorIdAndEditoraId(idAutor, idEditora);
    }
}
