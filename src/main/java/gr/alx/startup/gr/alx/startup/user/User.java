package gr.alx.startup.gr.alx.startup.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.alx.startup.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Audited
public class User extends BaseEntity {

    private String username;

    @JsonIgnore
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
