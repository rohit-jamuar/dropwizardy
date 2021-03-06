package com.dropwizard_application.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HashedStringRepresentation {
	
	private int generatedHashCode;
	private String hashInput; 
	
	public HashedStringRepresentation(int s, String str)
	{
		this.generatedHashCode = s;
		this.hashInput = str;
	}
	
	@JsonProperty
	public int generatedHashCode()
	{
		return this.generatedHashCode;
	}
	
	@JsonProperty
	public String hashInput()
	{
		return this.hashInput;
	}
	
	
}