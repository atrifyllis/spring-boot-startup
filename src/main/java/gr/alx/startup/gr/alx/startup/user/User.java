package gr.alx.startup.gr.alx.startup.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.alx.startup.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Audited
public class User extends BaseEntity {

    private String username;

    @JsonIgnore
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
