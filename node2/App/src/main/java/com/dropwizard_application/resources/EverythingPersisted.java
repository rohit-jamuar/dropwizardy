package com.dropwizard_application.resources;

import io.dropwizard.hibernate.UnitOfWork;

import com.dropwizard_application.core.AllCiphers;
import com.dropwizard_application.dB.InputOutputDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getEverything")
@Produces(MediaType.APPLICATION_JSON)
public class EverythingPersisted {
	
	private final InputOutputDAO d;
	
	public EverythingPersisted(InputOutputDAO dao)
	{
		this.d = dao;
	}

	@GET
	@UnitOfWork
	public AllCiphers getCipher()
	{
		return new AllCiphers(d.fetchAll());
	}
	
}