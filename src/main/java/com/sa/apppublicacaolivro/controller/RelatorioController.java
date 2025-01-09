package com.sa.apppublicacaolivro.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sa.apppublicacaolivro.model.Publicacao;
import com.sa.apppublicacaolivro.service.PublicacaoService;

@Controller
public class RelatorioController {

    @Autowired
    private PublicacaoService publicacaoService;

    @GetMapping("/gerar-relatorio-pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf() throws DocumentException, IOException {
        List<Publicacao> publicacao = publicacaoService.listarPublicacao(); // Método para buscar as publicações

        // Criação do documento
        Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Criação do PdfWriter
        PdfWriter.getInstance(doc, baos);

        // Abrir o documento
        doc.open();

        // Adicionando título
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Relatório de Publicação", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);

        // Adicionar uma linha em branco
        doc.add(new Paragraph("\n"));

        // Criando a tabela com 6 colunas
        PdfPTable table = new PdfPTable(6);

        // Definir os títulos das colunas
        table.addCell("Id publicação");
        table.addCell("Título");
        table.addCell("Ano Publicação");
        table.addCell("Gênero");
        table.addCell("Autor");
        table.addCell("Editor");

        // Adicionar os dados das publicações à tabela
        for (Publicacao pub : publicacao) {
            table.addCell(String.valueOf(pub.getIdPublicacao()));
            table.addCell(pub.getTitulo());
            table.addCell(String.valueOf(pub.getAno_publicacao()));
            table.addCell(pub.getGenero()); // Assumindo que a classe Publicacao tem o método getGenero()
            table.addCell(pub.getAutor() != null ? pub.getAutor().getNome() : "N/A");
            table.addCell(pub.getEditora() != null ? pub.getEditora().getNome() : "N/A");
        }

        // Adicionando a tabela ao documento
        doc.add(table);

        // Fechar o documento
        doc.close();

        // Retornar o PDF como resposta
        byte[] pdfBytes = baos.toByteArray();

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=relatorio_publicacoes.pdf")
                .body(pdfBytes);
    }
}
