package com.dropwizard_application.resources;

import io.dropwizard.hibernate.UnitOfWork;

import com.dropwizard_application.CipherRepresentation;
import com.dropwizard_application.core.HashedStringRepresentation;
import com.dropwizard_application.persistenceUnit.InputOutput;
import com.dropwizard_application.persistenceUnit.InputOutputDAO;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;

@Path("/getHash")
@Produces(MediaType.APPLICATION_JSON)
public class HashCodeResource {

	private final InputOutputDAO d;
	private final Client c;
	
	public HashCodeResource(InputOutputDAO d, Client clientFacilitator) { 
		this.d = d;
		this.c = clientFacilitator;
	}
	
	@GET
	@UnitOfWork
	public HashedStringRepresentation getHashCode(@QueryParam("str") Optional<String> str)
	{
		int generatedHash = str.or("Stranger").hashCode();
		String strPassed = str.or("Stranger");
		
		WebResource r = c.resource("http://localhost:9002/getCipher").queryParam("str", strPassed);
		CipherRepresentation response = r.accept(MediaType.APPLICATION_JSON_TYPE).get(CipherRepresentation.class);
		 d.create(new InputOutput(strPassed, generatedHash, response.ctxt()));
		return new HashedStringRepresentation(generatedHash, strPassed);
	}
	
	@Path("/{str}")
	@GET
	@UnitOfWork
	public HashedStringRepresentation searchHashCode(@PathParam("str") String str)
	{
		InputOutput result = d.findByInput(str).or(new InputOutput(String.format("notFound : %s", str), -1, "Random"));
		return new HashedStringRepresentation(result.getOutput(), result.getInput());
	}
	
}
