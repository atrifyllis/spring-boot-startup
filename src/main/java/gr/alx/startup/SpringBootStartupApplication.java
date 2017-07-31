package gr.alx.startup;

import gr.alx.startup.gr.alx.startup.user.User;
import gr.alx.startup.gr.alx.startup.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class SpringBootStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartupApplication.class, args);
    }

    @Component
    class ApplicationInitializer implements CommandLineRunner {

        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;

        public ApplicationInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {

            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... strings) throws Exception {
            userRepository.save(Arrays.asList(
                    new User("alex", passwordEncoder.encode("alex")),
                    new User("nikos", passwordEncoder.encode("nikos"))
            ));
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
