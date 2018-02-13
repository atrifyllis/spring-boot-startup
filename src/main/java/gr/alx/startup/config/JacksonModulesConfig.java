package gr.alx.startup.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonModulesConfig {

    @Bean
    public Module jacksonCustomMixins() {
        return new JacksonCustomMixins();
    }
}
