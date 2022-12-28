package lt.codeacademy.lebo.appuser.userprofile;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lt.codeacademy.lebo.appuser.AppUser;
import lt.codeacademy.lebo.fakebankacc.FakeBankAccount;
import lt.codeacademy.lebo.image.ProfileImage;
import lt.codeacademy.lebo.item.Item;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long profileID;
	private String address;
	private String city;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	private String chooseRole;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "img_id", referencedColumnName = "id")
	private ProfileImage profileImage;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_id", referencedColumnName = "bankId")
	private FakeBankAccount bankAccount;
	private Double walletBallance = 0d;

}
