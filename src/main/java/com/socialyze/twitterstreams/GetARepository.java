package com.socialyze.twitterstreams;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.socialyze.entity.Employee;

public interface GetARepository extends MongoRepository<Employee, String> {




	
}

