package gr.alx.startup.config;

import gr.alx.startup.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Register custom validators
 */
@Configuration
public class ValidationConfig extends RepositoryRestConfigurerAdapter {

    @Autowired
    private UserValidator userValidator;

    /**
     * Create a beanValidator to use in bean validation - by default if bean validation fails a generic error is thrown from sprin data rest
     */
    @Bean
    @Primary
    Validator beanValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", beanValidator());
        validatingListener.addValidator("beforeSave", beanValidator());

        validatingListener.addValidator("beforeCreate", userValidator);
        validatingListener.addValidator("beforeSave", userValidator);
    }
}
