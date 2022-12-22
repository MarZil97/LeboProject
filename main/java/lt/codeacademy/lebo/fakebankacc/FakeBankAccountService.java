package lt.codeacademy.lebo.fakebankacc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lt.codeacademy.lebo.appuser.userprofile.UserProfile;
import lt.codeacademy.lebo.appuser.userprofile.UserProfileService;

@Service
@AllArgsConstructor
public class FakeBankAccountService {

	@Autowired
	private FakeBankAccountRepository repository;
	
	@Autowired
	private UserProfileService userProfileService;
	
	public Optional<FakeBankAccount> findByAccountNumber(String accountNumber){
		return repository.findByName(accountNumber);
	}
	
	public void saveBankAccount(FakeBankAccount bankAccount) {
		repository.save(bankAccount);
	}
	
	public void setBankAccountToUser(UserProfile userProfile) {
		if(userProfile.getChooseRole().equalsIgnoreCase("BUYER")) {
			userProfile.setBankAccount(repository.findById(1l).orElseThrow(() -> new IllegalArgumentException("Error")));
		} else {
			userProfile.setBankAccount(repository.findById(2l).orElseThrow(() -> new IllegalArgumentException("Error")));
		}
	}
	
	public void depositToOnlineWallet(UserProfile userProfile, Double depositRequest) {
		if(userProfile.getBankAccount().getBankAccountBallance() - depositRequest < 0) {
			throw new IllegalArgumentException("Error");
		} else {
			userProfile.setWalletBallance(userProfile.getWalletBallance() + depositRequest);
			userProfileService.saveUserProfileInformation(userProfile);
			userProfile.getBankAccount().setBankAccountBallance(userProfile.getBankAccount().getBankAccountBallance() - depositRequest);
			repository.save(userProfile.getBankAccount());
		}
	}
}
