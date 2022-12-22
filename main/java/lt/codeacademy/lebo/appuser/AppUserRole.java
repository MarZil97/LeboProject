package lt.codeacademy.lebo.appuser;

import lombok.Getter;

@Getter
public enum AppUserRole {

	BUYER("BUYER"),
	SELLER("SELLER"),
	USER("USER");
	
	private String role;

	AppUserRole(String string) {
		this.role = string;
	}
	
	
	
}
