/*
 * ActorService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;
import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService extends AbstractService<Actor> {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ActorRepository actorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ActorService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Iterable<Actor> save(final Iterable<Actor> actors) {
		Assert.isTrue(actors != null);
		return this.actorRepository.save(actors);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Actor findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
