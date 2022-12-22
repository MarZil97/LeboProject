package lt.codeacademy.lebo.fakebankacc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class FakeBankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bankId;
	
	private String name;
	private Double bankAccountBallance;

}
