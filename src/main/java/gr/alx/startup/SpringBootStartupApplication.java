package gr.alx.startup;

import gr.alx.startup.gr.alx.startup.user.User;
import gr.alx.startup.gr.alx.startup.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

        public ApplicationInitializer(UserRepository userRepository) {

            this.userRepository = userRepository;
        }

        @Override
        public void run(String... strings) throws Exception {
            userRepository.save(Arrays.asList(
                    new User("alex"),
                    new User("nikos")
            ));
        }
    }
}
