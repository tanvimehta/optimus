package com.uplan.service;

import com.uplan.core.ResetPasswordToken;
import com.uplan.core.Template;
import com.uplan.core.User;
import com.uplan.db.ResetPasswordTokenDAO;
import com.uplan.db.UserDAO;
import com.uplan.health.TemplateHealthCheck;
import com.uplan.resources.HelloWorldResource;
import com.uplan.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.hibernate.HibernateBundle;


public class UPlanApplication extends Application<UPlanConfiguration> {

    public static void main(String[] args) throws Exception {
        new UPlanApplication().run(args);
    }

    private final HibernateBundle<UPlanConfiguration> hibernate = new HibernateBundle<UPlanConfiguration>(
            User.class,
            ResetPasswordToken.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(UPlanConfiguration uPlanConfiguration) {
            return uPlanConfiguration.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<UPlanConfiguration> migration = new MigrationsBundle<UPlanConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(UPlanConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<UPlanConfiguration> bootstrap) {
        bootstrap.addBundle(migration);
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(UPlanConfiguration configuration, Environment environment) {
        final Template template = configuration.buildTemplate();
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        final ResetPasswordTokenDAO resetPasswordTokenDAO = new ResetPasswordTokenDAO(hibernate.getSessionFactory());

        environment.healthChecks().register("template", new TemplateHealthCheck(template));
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new UserResource(userDAO, resetPasswordTokenDAO));

    }
}
