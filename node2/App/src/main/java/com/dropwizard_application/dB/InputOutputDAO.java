package com.dropwizard_application.dB;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class InputOutputDAO extends AbstractDAO<InputOutput>{

	public InputOutputDAO(SessionFactory factory)
	{
		super(factory);
	}
	
	public void create(InputOutput i) 
	{
		if (!doesInputStringExist(i.getInput()))
			persist(i);
	}
	
	@SuppressWarnings("unchecked")
	public List<InputOutput> fetchAll()
	{
		return currentSession().createCriteria(InputOutput.class).list();
	}
	
	private boolean doesInputStringExist(String s)
	{
		Criteria c1 = currentSession().createCriteria(InputOutput.class).add(Restrictions.eq("input",s));
		return uniqueResult(c1) != null ? true : false;
	}
	
}