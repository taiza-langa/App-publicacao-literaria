package com.sa.apppublicacaolivro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.apppublicacaolivro.exception.EditoraComPublicacoesException;
import com.sa.apppublicacaolivro.model.Editora;
import com.sa.apppublicacaolivro.model.Publicacao;
import com.sa.apppublicacaolivro.repository.RepositoryEditora;

@Service
public class EditoraService {

    @Autowired
    private RepositoryEditora repositoryEditora;

    @Autowired
    private PublicacaoService publicacaoService;

    public Editora salvarEditora(Editora editora) {
        return repositoryEditora.save(editora);
    }

    public List<Editora> listarEditora() {
        return repositoryEditora.findAll();
    }

    public Editora buscarPorId(Long id) {
        Optional<Editora> editora = repositoryEditora.findById(id);
        return editora.orElseThrow(() -> new RuntimeException("Editora não encontrado"));
    }

    public void deletarPorId(Long id_editora) {
        List<Publicacao> publicacoes = publicacaoService.listarPublicacoesPorEditora(id_editora);
        if (publicacoes.isEmpty()) {
            repositoryEditora.deleteById(id_editora);
        } else {
            throw new EditoraComPublicacoesException(
                    "Não é possível deletar. Existem publicações associadas a esta editora.");
        }
    }
}
