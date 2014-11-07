package com.dropwizard_application.resources;

import io.dropwizard.hibernate.UnitOfWork;

import com.dropwizard_application.core.HashedStringRepresentation;
import com.dropwizard_application.persistenceUnit.InputOutput;
import com.dropwizard_application.persistenceUnit.InputOutputDAO;
import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/getHash")
@Produces(MediaType.APPLICATION_JSON)
public class HashCodeResource {

	private final InputOutputDAO d;
	
	public HashCodeResource(InputOutputDAO d) { 
		this.d = d;
	}
	
	@GET
	@UnitOfWork
	public HashedStringRepresentation getHashCode(@QueryParam("str") Optional<String> str)
	{
		int generatedHash = str.or("Stranger").hashCode();
		String strPassed = str.or("Stranger");
		d.create(new InputOutput(strPassed, generatedHash));
		return new HashedStringRepresentation(generatedHash, strPassed);
	}
	
	@Path("/{str}")
	@GET
	@UnitOfWork
	public HashedStringRepresentation searchHashCode(@PathParam("str") String str)
	{
		InputOutput result = d.findByInput(str).or(new InputOutput(String.format("notFound : %s", str), -1));
		return new HashedStringRepresentation(result.getOutput(), result.getInput());
	}
	
}