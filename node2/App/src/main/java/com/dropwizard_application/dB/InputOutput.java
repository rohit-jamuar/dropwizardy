package com.dropwizard_application.dB;

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
	
	@Column(name = "outputstr", nullable = false)
	private String output;
	
	public InputOutput() {}
	
	public InputOutput(String input, String output) {
		this.input = input;
		this.output = output;
	}

	public String getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}
}