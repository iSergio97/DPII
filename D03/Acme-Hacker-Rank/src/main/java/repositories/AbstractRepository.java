
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DomainEntity;

@Repository
public interface AbstractRepository<T extends DomainEntity> extends JpaRepository<T, Integer> {

}
