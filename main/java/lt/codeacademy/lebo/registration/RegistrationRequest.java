package lt.codeacademy.lebo.registration;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class RegistrationRequest {

	private String firstName;
	private String lastName;
	private String password;
	private String email;
}
