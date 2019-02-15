package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.IsRegistered;

@Repository
public interface IsRegisteredRepository extends JpaRepository<IsRegistered, Integer> {

}
