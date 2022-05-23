package br.com.pdfgenerate.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class GeneratePdfService {

	public void generatePdf() throws DocumentException, FileNotFoundException {
		
		File file = new File("src/main/resources/file.pdf");
		
		Document doc = new Document();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		PdfWriter.getInstance(doc, baos);
//		PdfWriter.getInstance(doc, new FileOutputStream(file));
		
		doc.open();
		
		doc.add(new Paragraph("Gerando pdf2."));
		
		doc.close();
		
		FileOutputStream fos = new FileOutputStream(file);
		try {
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ByteArrayOutputStream generatePdfStream(String text) {
				
		Document doc = new Document();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(doc, baos);
			
			doc.open();
			
			doc.add(new Paragraph(text));
			
			doc.close();
			
			baos.flush();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		return baos;
		
	}
	
}
