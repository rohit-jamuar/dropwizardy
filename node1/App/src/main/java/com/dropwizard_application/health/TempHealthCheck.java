package com.dropwizard_application.health;

import com.codahale.metrics.health.HealthCheck;

public class TempHealthCheck  extends HealthCheck{
	
	public TempHealthCheck() {}

	@Override
	protected Result check() throws Exception {
		// TODO Auto-generated method stub
		return Result.healthy();
	}
	
	
}
