package br.com.pdfgenerate.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class DataSourceImpl implements DataSource {

	private String name;
	private String contentType;
	private ByteArrayOutputStream baos;
	
	public DataSourceImpl() {}

	public DataSourceImpl(String name, String contentType, ByteArrayOutputStream baos) {
		this.name = name;
		this.contentType = contentType;
		this.baos = baos;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getName() {
		return name;
	}

}
