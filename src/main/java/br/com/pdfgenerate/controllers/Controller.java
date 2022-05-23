package br.com.pdfgenerate.controllers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pdfgenerate.constants.ContentType;
import br.com.pdfgenerate.services.EmailService;
import br.com.pdfgenerate.services.GeneratePdfService;
import br.com.pdfgenerate.utils.ZipGenerate;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	private GeneratePdfService generatePdfService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("pdf")
	public void pdfGenerate() {
		try {
			int count = 1;
			HashMap<String, ByteArrayOutputStream> baosMap = new HashMap<>();
			while(count < 4) {
				ByteArrayOutputStream baos = generatePdfService.generatePdfStream(String.format("Gerando pdf %d.", count));
				baosMap.put(String.format("file%d.pdf", count), baos);
				count++;
			}			
			ByteArrayOutputStream baosZip = ZipGenerate.zipStreamMapFiles(baosMap);
			emailService.sendForAttachment("Juan", "juanpapiro@hotmail.com", "file.zip", ContentType.ZIP, baosZip);
			baosZip.reset();
			baosZip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
