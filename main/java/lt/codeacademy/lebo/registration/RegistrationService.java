package lt.codeacademy.lebo.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lt.codeacademy.lebo.appuser.AppUser;
import lt.codeacademy.lebo.appuser.AppUserRole;
import lt.codeacademy.lebo.appuser.AppUserService;

@Service
@AllArgsConstructor
public class RegistrationService {

	@Autowired
	private final AppUserService appUserService;
	@Autowired
	private final EmailValidator emailValidator;
	
	
	public String register(RegistrationRequest request) {
		
		boolean isValidEmail = emailValidator.test(request.getEmail());
		
		if(!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		return appUserService.signUpUser(new AppUser(request.getFirstName(),
										             request.getLastName(),
										             request.getEmail(),
										             request.getPassword(),
										             AppUserRole.USER));
	}
}
