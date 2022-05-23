package br.com.pdfgenerate.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipGenerate {

	
	public static ByteArrayOutputStream zipStreamMapFiles(HashMap<String, ByteArrayOutputStream> baosMap) {

		int bufferSize = 1024;
		int cont;
	    byte[] dados = new byte[bufferSize];
		
	    ByteArrayOutputStream baosZip = new ByteArrayOutputStream();
	    ZipOutputStream zipOutputStream = new ZipOutputStream(baosZip);
	    BufferedInputStream bufferedInputStream;
	    ZipEntry zipEntry;
	    try {
	    	for(Map.Entry<String, ByteArrayOutputStream> map : baosMap.entrySet()) {
	    		bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(map.getValue().toByteArray()), bufferSize);
	    		zipEntry = new ZipEntry(map.getKey());
	    		zipOutputStream.putNextEntry(zipEntry);
	    		
	    		while ((cont = bufferedInputStream.read(dados, 0, bufferSize)) != -1) {
	    			zipOutputStream.write(dados, 0, cont);
	    		}
	    		
	    		zipOutputStream.closeEntry();
	    		bufferedInputStream.close();
	    	}
	    	zipOutputStream.flush();
	    	zipOutputStream.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		return baosZip;
	}
	
	

}
