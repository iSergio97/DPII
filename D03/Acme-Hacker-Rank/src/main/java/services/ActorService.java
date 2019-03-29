/*
 * ActorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ActorRepository	actorRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ActorService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Actor save(final Actor actor) {
		Assert.isTrue(actor != null);
		return this.actorRepository.save(actor);
	}

	public Iterable<Actor> save(final Iterable<Actor> actors) {
		Assert.isTrue(actors != null);
		return this.actorRepository.save(actors);
	}

	public void delete(final Actor actor) {
		Assert.isTrue(actor != null);
		this.actorRepository.delete(actor);
	}

	public void delete(final Iterable<Actor> actors) {
		Assert.isTrue(actors != null);
		this.actorRepository.delete(actors);
	}

	public Actor findOne(final int id) {
		return this.actorRepository.findOne(id);
	}

	public List<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
