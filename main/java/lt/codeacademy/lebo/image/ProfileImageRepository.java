package lt.codeacademy.lebo.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
	
	Optional<ProfileImage> findByName(String fileName);

}
