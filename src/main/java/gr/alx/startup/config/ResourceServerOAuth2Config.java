package gr.alx.startup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@EnableResourceServer
@Configuration
public class ResourceServerOAuth2Config extends ResourceServerConfigurerAdapter {

    @Autowired
    DefaultTokenServices tokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // this is required otherwise the oauth filter is used for login page and it fails!!!
                .requestMatchers()
                .antMatchers("/api/**")

                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices);
    }
}
