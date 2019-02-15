
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.FixUpTaskCategory;

@Repository
public interface FixUpTaskCategoryRepository extends JpaRepository<FixUpTaskCategory, Integer> {

}
