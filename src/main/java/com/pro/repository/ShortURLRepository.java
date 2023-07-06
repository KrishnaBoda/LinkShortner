package com.pro.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pro.entity.ShortURLEntity;

@Repository
public interface ShortURLRepository extends MongoRepository<ShortURLEntity, String> {
	
	@Query("{'randomString': ?0}")
	List<ShortURLEntity> findURLExist(String randomString);

}
