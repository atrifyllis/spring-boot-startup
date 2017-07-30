package gr.alx.startup.gr.alx.startup.user;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    public UUID id;
    private String username;

    public User(String username) {
        this.username = username;
    }
}
