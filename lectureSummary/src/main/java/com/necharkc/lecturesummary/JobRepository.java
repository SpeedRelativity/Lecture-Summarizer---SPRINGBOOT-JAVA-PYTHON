package com.necharkc.lecturesummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, String> { // the entity and the type of primary key (Entity = Job, id = String)

}
