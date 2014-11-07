package com.dropwizard_application;

import com.dropwizard_application.health.TempHealthCheck;
import com.dropwizard_application.persistenceUnit.InputOutput;
import com.dropwizard_application.persistenceUnit.InputOutputDAO;
import com.dropwizard_application.resources.HashCodeResource;
import com.sun.jersey.api.client.Client;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<Config>
{

	private final HibernateBundle<Config> hibernate = new HibernateBundle<Config>(InputOutput.class) {
	    @Override
	    public DataSourceFactory getDataSourceFactory(Config Config) {
	        return Config.getDataSourceFactory();
	    }
	};
	
	public static void main(String[] args) throws Exception {
	        new App().run(args);
	    }
	 
	@Override
	public void initialize(Bootstrap<Config> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<Config>() {
            @Override
            public DataSourceFactory getDataSourceFactory(Config configuration) {
                return configuration.getDataSourceFactory();
            }
        });
		bootstrap.addBundle(hibernate);
		
	}

	@Override
	public void run(Config Config, Environment environment) throws Exception {
		final TempHealthCheck h = new TempHealthCheck();
		environment.healthChecks().register("alwaysHappy", h);
		
		final InputOutputDAO dao = new InputOutputDAO(hibernate.getSessionFactory());
		final HashCodeResource r = new HashCodeResource(dao);
		environment.jersey().register(r);
		
		final Client client = new JerseyClientBuilder(environment).using(Config.getJerseyClientConfiguration())
                .build(getName());
		environment.addResource(new ExternalServiceResource(client));
		
	}
}
