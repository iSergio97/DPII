/*
 * ChapterService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChapterRepository;
import domain.Chapter;

@Service
@Transactional
public class ChapterService extends AbstractService<ChapterRepository, Chapter> {

}
