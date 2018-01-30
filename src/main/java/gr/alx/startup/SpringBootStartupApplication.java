package gr.alx.startup;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartupApplication.class, args);
    }

    /**
     * Repair database before migration. This seems that it should be default...
     *
     * @return
     */
    @Bean
    @Profile({"prod", "cloud"})
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {
                flyway.repair();
                flyway.migrate();
            }
        };

        return strategy;
    }
}
