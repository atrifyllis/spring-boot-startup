package gr.alx.startup.gr.alx.startup.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.alx.startup.common.BaseEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class User extends BaseEntity {

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
