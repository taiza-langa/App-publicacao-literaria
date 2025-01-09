package com.sa.apppublicacaolivro.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sa.apppublicacaolivro.exception.EditoraComPublicacoesException;
import com.sa.apppublicacaolivro.model.Autor;
import com.sa.apppublicacaolivro.service.AutorService;

@Controller
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/autor")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("autor", new Autor());
        return "autor";
    }

    @PostMapping("/autor")
    public String cadastrarAutor(Autor autor, MultipartFile file) throws IOException {
        autorService.salvarAutor(autor);
        return "redirect:/autor";
    }

    @GetMapping("/listarAutor")
    public String listarAutor(Model model) {
        model.addAttribute("listarAutor", autorService.listarAutor());
        model.addAttribute("error", "Não é possível deletar este autor pois há publicações associadas.");
        return "listarAutor";
    }

    @PostMapping("/autor/deletar/{id_autor}")
    public String deletarPublicacao(@PathVariable Long id_autor, RedirectAttributes redirectAttributes) {
        try {
            autorService.deletarPorId(id_autor);
            redirectAttributes.addFlashAttribute("sucesso", "Autor deletado com sucesso.");
        } catch (EditoraComPublicacoesException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/listarAutor";
    }

    @GetMapping("/autor/editar/{id_autor}")
    public String mostrarFormularioEdicao(@PathVariable Long id_autor, Model model) {
        Autor autor = autorService.buscarPorId(id_autor);
        if (autor != null) {
            model.addAttribute("autor", autor);
            return "editar_autor";
        } else {
            return "redirect:/autor/lista";
        }
    }

    @PostMapping("/autor/editar")
    public String editarAutor(@ModelAttribute Autor autor) {
        autorService.salvarAutor(autor);
        return "redirect:/listarAutor";
    }

    @PostMapping("/autor/salvar")
    public String salvarAutor(@ModelAttribute Autor autor) {
        autorService.salvarAutor(autor);
        return "redirect:/listarAutor";
    }
}
