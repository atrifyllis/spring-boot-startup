package gr.alx.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;


//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from user where username=?")
                .authoritiesByUsernameQuery(
                        "select u.username, r.roles from user_roles r join user u on r.user_id=u.id where username=? and enabled=true")
                .passwordEncoder(passwordEncoder())
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .requireCsrfProtectionMatcher(csrfRequestMatcher)
//
//                .and()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/h2-console/**").authenticated()
//                // this is needed for h2-console to work, could be removed in production
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//
//                .and()
//                .httpBasic()
//        ;
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    RequestMatcher csrfRequestMatcher = new RequestMatcher() {

        // Disable CSFR protection on the following urls:
        private AntPathRequestMatcher[] requestMatchers = {
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/logout"),
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/api/**")
        };

        @Override
        public boolean matches(HttpServletRequest request) {
            // If the request match one url the CSFR protection will be disabled
            for (AntPathRequestMatcher rm : requestMatchers) {
                if (rm.matches(request)) {
                    return false;
                }
            }
            return true;
        }
    };
}


