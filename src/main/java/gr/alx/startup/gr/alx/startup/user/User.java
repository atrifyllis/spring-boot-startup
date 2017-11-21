package gr.alx.startup.gr.alx.startup.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.alx.startup.common.BaseEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class User extends BaseEntity {

    public static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Size(min = 4)
    private String username;

    @Email
    private String email;

    /**
     * Never send the password to the UI!
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * The confirmation password is only for validation purposes so it is marked as Transient
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String confirmPassword;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * In case we pass password from the UI, it must be encoded before it is inserted in the db.
     */
    @PreUpdate
    @PrePersist
    private void encodePassword() {
        password = !StringUtils.isEmpty(password) ? ENCODER.encode(password) : null;
    }
}
