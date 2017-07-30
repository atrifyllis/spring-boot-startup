package gr.alx.startup.common;

import org.hibernate.envers.Audited;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Data;

@Data
@MappedSuperclass
@Audited
public abstract class BaseEntity {

	@Id
	@GeneratedValue
	private UUID id;

	@Version
	private Long version;
}
