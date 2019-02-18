
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

}
