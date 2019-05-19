/*
 * GenreService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.GenreRepository;
import domain.Genre;

@Service
@Transactional
public class GenreService extends AbstractService<GenreRepository, Genre> {

}
