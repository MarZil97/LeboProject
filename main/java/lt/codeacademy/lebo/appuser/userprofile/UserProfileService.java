package lt.codeacademy.lebo.appuser.userprofile;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lt.codeacademy.lebo.appuser.AppUser;
import lt.codeacademy.lebo.appuser.AppUserRole;

@Service
public class UserProfileService {

	@Autowired
	UserProfileRepository profileRepository;
	
	public void saveUserProfileInformation(UserProfile userProfile) {
		profileRepository.save(userProfile);
	}
	
	public String getLoggedInUserUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
		username = ((UserDetails)principal).getUsername();
		} else {
		username = principal.toString();
		}
		
		return username;
	}
	
	public void changeUserRole(UserProfile userProfile, AppUser appUser) {
		
		if(userProfile.getChooseRole().equalsIgnoreCase("BUYER")) {
			appUser.setAppUserRole(AppUserRole.BUYER);
		} else {
			appUser.setAppUserRole(AppUserRole.SELLER);
		}
	}
	
	public Optional<UserProfile> findById(Long id) {
		return profileRepository.findById(id);
	}
}
