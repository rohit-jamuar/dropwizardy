package com.dropwizard_application.persistenceUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "inputoutput")
public class InputOutput {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "inputstr", nullable = false)
	private String input;
	
	@Column(name = "outputint", nullable = false)
	private int output;
	
	@Column(name = "ctxt", nullable = false)
	private String ctxt;
	
	public InputOutput(String input, int output, String ctxt) {
		this.input = input;
		this.output = output;
		this.ctxt = ctxt;
	}

	public String getInput() {
		return input;
	}

	public int getOutput() {
		return output;
	}
	
	public String getCtxt() {
		return ctxt;
	}
}
