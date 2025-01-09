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
import com.sa.apppublicacaolivro.model.Editora;
import com.sa.apppublicacaolivro.service.EditoraService;

@Controller
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @GetMapping("/editora")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("editora", new Editora());
        return "editora";
    }

    @PostMapping("/editora")
    public String cadastrarEditora(Editora editora, MultipartFile file) throws IOException {
        editoraService.salvarEditora(editora);
        return "redirect:/editora";
    }

    @GetMapping("/listarEditora")
    public String listarEditora(Model model) {
        model.addAttribute("listarEditora", editoraService.listarEditora());
        model.addAttribute("error", "Não é possível deletar esta editora pois há publicações associadas.");
        return "listarEditora";
    }

    @PostMapping("/editora/deletar/{idEditora}")
    public String deletarPublicacao(@PathVariable Long idEditora, RedirectAttributes redirectAttributes) {
        try {
            editoraService.deletarPorId(idEditora);
            redirectAttributes.addFlashAttribute("sucesso", "Editora deletada com sucesso.");
        } catch (EditoraComPublicacoesException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/listarEditora";
    }

    @GetMapping("/editora/editar/{idEditora}")
    public String mostrarFormularioEdicao(@PathVariable Long idEditora, Model model) {
        Editora editora = editoraService.buscarPorId(idEditora);
        if (editora != null) {
            model.addAttribute("editora", editora);
            return "editar_editora";
        } else {
            return "redirect:/editora/lista";
        }
    }

    @PostMapping("/editora/editar")
    public String editarEditora(@ModelAttribute Editora editora) {
        editoraService.salvarEditora(editora);
        return "redirect:/listarEditora";
    }

    @PostMapping("/editora/salvar")
    public String salvarEditora(@ModelAttribute Editora editora) {
        editoraService.salvarEditora(editora);
        return "redirect:/listarEditora";
    }
}
