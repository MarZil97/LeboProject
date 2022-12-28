package lt.codeacademy.lebo.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.codeacademy.lebo.appuser.userprofile.UserProfile;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private Long itemId;
	private String name;
	private String description;
	private Double price;
	@JoinColumn(name = "profile_id", referencedColumnName = "profileID")
	@ManyToOne
	private UserProfile userProfile;

}
