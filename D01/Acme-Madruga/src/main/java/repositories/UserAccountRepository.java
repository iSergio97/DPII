package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

}
