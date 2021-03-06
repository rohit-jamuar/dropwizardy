package com.dropwizard_application.resources;

import java.util.HashMap;

import io.dropwizard.hibernate.UnitOfWork;

import com.dropwizard_application.core.CipherRepresentation;
import com.dropwizard_application.dB.InputOutput;
import com.dropwizard_application.dB.InputOutputDAO;
import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/getCipher")
@Produces(MediaType.APPLICATION_JSON)
public class CipherResource {

	private final InputOutputDAO d;
	private static HashMap<Integer, Character> intToCharMap = new HashMap<Integer, Character>();
	private static HashMap<Character, Integer> charToIntMap = new HashMap<Character, Integer>();
	
	public CipherResource(InputOutputDAO d) { 
		if (intToCharMap.isEmpty() || charToIntMap.isEmpty())
		{
			char[] characters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			for (int i = 0; i<characters.length; i++)
			{
				intToCharMap.put(i, characters[i]);
				charToIntMap.put(characters[i], i);
			}
		}
		this.d = d;
	}
	
	private String generateCeaserCipher(String s, Optional<Integer> key)
	{
		int newIndex;
		StringBuilder result = new StringBuilder();
		for (int i=0; i<s.length(); i++)
		{
			char currentChar = s.charAt(i);
			if ('a' <= currentChar &&  currentChar <= 'z')
			{
				newIndex = (charToIntMap.get(currentChar) + key.or(3)) % 26;
				result.append(intToCharMap.get(newIndex));
			}
			else
			{
				if ('A' <= currentChar &&  currentChar <= 'Z')
				{
					newIndex = (charToIntMap.get(Character.toLowerCase(currentChar)) + key.or(3)) % 26;
					result.append(Character.toUpperCase(intToCharMap.get(newIndex)));
				}
				else
				{
					result.append(currentChar);
				}
			}
		}
		return result.toString();
	}
	
	@GET
	@UnitOfWork
	public CipherRepresentation getCipher(@QueryParam("str") Optional<String> str)
	{
		String strPassed = str.or("StrangerDanger");
		String cipherProduced = generateCeaserCipher(strPassed, Optional.of(5));
		d.create(new InputOutput(strPassed, cipherProduced));
		return new CipherRepresentation(cipherProduced);
	}
}