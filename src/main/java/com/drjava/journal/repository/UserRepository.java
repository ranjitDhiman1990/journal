package com.drjava.journal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.drjava.journal.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
