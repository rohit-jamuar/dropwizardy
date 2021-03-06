package com.dropwizard_application;

import com.dropwizard_application.doctor.Doctor;
import com.dropwizard_application.dB.InputOutput;
import com.dropwizard_application.dB.InputOutputDAO;
import com.dropwizard_application.resources.CipherResource;
import com.dropwizard_application.resources.EverythingPersisted;

import io.dropwizard.Application;
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
		final Doctor h = new Doctor();
		environment.healthChecks().register("alwaysHappy", h);
		
		final InputOutputDAO dao = new InputOutputDAO(hibernate.getSessionFactory());
		
		final CipherResource r1 = new CipherResource(dao);
		environment.jersey().register(r1);
		
		final EverythingPersisted r2 = new EverythingPersisted(dao);
		environment.jersey().register(r2);
		
	}
}
