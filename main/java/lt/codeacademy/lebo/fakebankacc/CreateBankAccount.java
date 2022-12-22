package lt.codeacademy.lebo.fakebankacc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateBankAccount {

	@Autowired
	private FakeBankAccountService bankService;
	
	@Bean
	public void bankAccount() {
		FakeBankAccount bankAccount1 = new FakeBankAccount();
		bankAccount1.setBankId(1l);
		bankAccount1.setName("Lt1234");
		bankAccount1.setBankAccountBallance(2000d);
		bankService.saveBankAccount(bankAccount1);
		
		FakeBankAccount bankAccount2 = new FakeBankAccount();
		bankAccount2.setBankId(2l);
		bankAccount2.setName("Lt5678");
		bankAccount2.setBankAccountBallance(0d);
		bankService.saveBankAccount(bankAccount2);
		
	}
}
