/*
 * MiscellaneousDataRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousData;

@Repository
public interface MiscellaneousDataRepository extends JpaRepository<MiscellaneousData, Integer> {

}
