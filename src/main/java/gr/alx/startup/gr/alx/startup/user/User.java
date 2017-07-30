package gr.alx.startup.gr.alx.startup.user;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

import gr.alx.startup.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Audited
public class User extends BaseEntity {

	private String username;

	public User(String username) {
		this.username = username;
	}
}
