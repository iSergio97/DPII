
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTaskCategory extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String							name;

	// Relationships ----------------------------------------------------------

	private Collection<FixUpTask>			fixUpTasks;
	private FixUpTaskCategory				fixUpTaskCategoryParent;
	private Collection<FixUpTaskCategory>	fixUpTaskCategoryChildren;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@OneToMany(mappedBy = "fixUpTaskCategory")
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	@Valid
	@ManyToOne(optional = false)
	public FixUpTaskCategory getFixUpTaskCategoryParent() {
		return this.fixUpTaskCategoryParent;
	}

	public void setFixUpTaskCategoryParent(final FixUpTaskCategory fixUpTaskCategoryParent) {
		this.fixUpTaskCategoryParent = fixUpTaskCategoryParent;
	}

	@OneToMany(mappedBy = "fixUpTaskCategoryParent")
	public Collection<FixUpTaskCategory> getFixUpTaskCategoryChildren() {
		return this.fixUpTaskCategoryChildren;
	}

	public void setFixUpTaskCategoryChildren(final Collection<FixUpTaskCategory> fixUpTaskCategoryChildren) {
		this.fixUpTaskCategoryChildren = fixUpTaskCategoryChildren;
	}

}
