package lt.codeacademy.lebo.fakebankacc;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FakeBankAccountRepository extends JpaRepository<FakeBankAccount, Long>{
	
	Optional<FakeBankAccount> findByName(String name);

}
