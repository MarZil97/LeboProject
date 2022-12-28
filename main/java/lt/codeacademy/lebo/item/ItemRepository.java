package lt.codeacademy.lebo.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.codeacademy.lebo.appuser.userprofile.UserProfile;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	public List<Item> findAllByUserProfile(UserProfile userProfile);

}
