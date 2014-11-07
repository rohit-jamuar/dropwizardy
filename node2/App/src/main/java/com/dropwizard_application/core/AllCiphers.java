package com.dropwizard_application.core;

import java.util.HashMap;
import java.util.List;

import com.dropwizard_application.dB.InputOutput;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AllCiphers {
	
	private List<InputOutput> allData;
	
	public AllCiphers(List<InputOutput> everything)
	{
		this.allData = everything;
	}
	
	@JsonProperty
	public HashMap<String, String> everything()
	{
		HashMap<String, String> dataFetched = new HashMap<String, String>();
		for (InputOutput i : this.allData)
			dataFetched.put(i.getInput(), i.getOutput());
		return dataFetched;
	}
}

