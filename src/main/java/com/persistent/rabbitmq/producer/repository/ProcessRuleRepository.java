package com.persistent.rabbitmq.producer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.persistent.rabbitmq.producer.model.Rules;

@Repository
public interface ProcessRuleRepository extends MongoRepository<Rules, String> {

}
