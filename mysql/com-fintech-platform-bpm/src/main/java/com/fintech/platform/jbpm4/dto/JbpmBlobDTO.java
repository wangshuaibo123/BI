package com.fintech.platform.jbpm4.dto;

import java.io.Serializable;


public class JbpmBlobDTO  implements Serializable{
	
	private Long id;
	
	private byte[] contents;

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

