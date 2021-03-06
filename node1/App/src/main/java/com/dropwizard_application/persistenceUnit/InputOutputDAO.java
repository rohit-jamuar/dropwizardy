package com.dropwizard_application.persistenceUnit;

import com.google.common.base.Optional;

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
	
	public Optional<InputOutput> findByInput(String s)
	{
		Criteria cr = currentSession().createCriteria(InputOutput.class);
		cr.add(Restrictions.eq("input",s));
		return Optional.fromNullable(uniqueResult(cr));
	}
	
	private boolean doesInputStringExist(String s)
	{
		Criteria c1 = currentSession().createCriteria(InputOutput.class).add(Restrictions.eq("input",s));
		return uniqueResult(c1) != null ? true : false;
	}
	
}
