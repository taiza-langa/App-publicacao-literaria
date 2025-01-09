package com.sa.apppublicacaolivro.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sa.apppublicacaolivro.exception.PublicacaoException;
import com.sa.apppublicacaolivro.model.Autor;
import com.sa.apppublicacaolivro.model.Editora;
import com.sa.apppublicacaolivro.model.Publicacao;
import com.sa.apppublicacaolivro.service.AutorService;
import com.sa.apppublicacaolivro.service.EditoraService;
import com.sa.apppublicacaolivro.service.PublicacaoService;

@Controller
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    @Autowired
    private EditoraService editoraService;

    @Autowired
    private AutorService autorService;

    @GetMapping("/telaInicial")
    public String mostrarTelaInicial(Model model) {
        return "telaInicial";
    }

    @GetMapping("/publicacao")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("publicacao", new Publicacao());
        model.addAttribute("listarEditora", editoraService.listarEditora());
        model.addAttribute("listarAutor", autorService.listarAutor());
        return "publicacao";
    }

    @PostMapping("/publicacao")
    public String cadastrarPublicacao(Publicacao publicacao, @RequestParam Long id_autor, @RequestParam Long id_editora)
            throws IOException {
        Autor autor = autorService.buscarPorId(id_autor);
        Editora editora = editoraService.buscarPorId(id_editora);

        publicacao.setAutor(autor);

        publicacao.setEditora(editora);

        publicacaoService.salvarPublicacao(publicacao);
        return "redirect:/publicacao";
    }

    @GetMapping("/listar")
    public String listarPublicacao(
            @RequestParam(value = "id_autor", required = false) Long id_autor,
            @RequestParam(value = "id_editora", required = false) Long id_editora,
            Model model) {

        List<Publicacao> publicacoes;

        if (id_autor != null && id_editora != null) {
            publicacoes = publicacaoService.listarPublicacoesPorAutorEEditora(id_autor, id_editora);
        } else if (id_autor != null) {
            publicacoes = publicacaoService.listarPublicacoesPorAutor(id_autor);
        } else if (id_editora != null) {
            publicacoes = publicacaoService.listarPublicacoesPorEditora(id_editora);
        } else {
            publicacoes = publicacaoService.listarPublicacao();
        }

        model.addAttribute("listarAutor", autorService.listarAutor());
        model.addAttribute("listarEditora", editoraService.listarEditora());
        model.addAttribute("listar", publicacoes);
        return "listar";
    }

    @PostMapping("/publicacao/deletar/{idPublicacao}")
    public String deletarPublicacao(@PathVariable Long idPublicacao, RedirectAttributes redirectAttributes) {
        try {
            publicacaoService.deletarPorId(idPublicacao);
            redirectAttributes.addFlashAttribute("sucesso", "Publicação deletada com sucesso.");
        } catch (PublicacaoException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/listar";
    }

    @GetMapping("/publicacao/editar/{idPublicacao}")
    public String mostrarFormularioEdicao(@PathVariable Long idPublicacao, Model model,
            RedirectAttributes redirectAttributes) {
        Publicacao publicacao = publicacaoService.buscarPorId(idPublicacao);
        if (publicacao != null) {
            model.addAttribute("publicacao", publicacao);
            model.addAttribute("listarEditora", editoraService.listarEditora());
            model.addAttribute("listarAutor", autorService.listarAutor());
            return "editar_publicacao";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Publicação não encontrada.");
            return "redirect:/listar";
        }
    }

    @PostMapping("/publicacao/editar")
    public String editarPublicacao(@ModelAttribute Publicacao publicacao,
            @RequestParam Long id_autor,
            @RequestParam Long id_editora) {

        Autor autor = autorService.buscarPorId(id_autor);
        Editora editora = editoraService.buscarPorId(id_editora);

        publicacao.setAutor(autor);
        publicacao.setEditora(editora);

        publicacaoService.salvarPublicacao(publicacao);

        return "redirect:/listar";
    }
}
