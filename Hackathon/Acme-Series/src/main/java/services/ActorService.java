/*
 * ActorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService extends AbstractService<ActorRepository, Actor> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Actor findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Actor findPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else
			return this.findByUserAccountId(LoginService.getPrincipal().getId());
	}

	public Actor findByUsername(final String username) {
		return this.repository.findByUsername(username);
	}

}
