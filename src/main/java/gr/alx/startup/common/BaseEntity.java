package gr.alx.startup.common;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@Data
@MappedSuperclass
@Audited
public abstract class BaseEntity {

    @Id
    @Type(type="uuid-char") //saved as varchar in database
    @GeneratedValue
    private UUID id;

    @Version
    private Long version;

    private Boolean enabled = true;
}
