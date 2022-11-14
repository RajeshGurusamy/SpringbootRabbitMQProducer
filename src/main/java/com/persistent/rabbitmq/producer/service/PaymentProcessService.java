package com.persistent.rabbitmq.producer.service;

import java.io.Serializable;
import java.util.List;

import com.persistent.rabbitmq.producer.model.RiskMonitor;
import com.persistent.rabbitmq.producer.model.Rules;

public interface PaymentProcessService extends Serializable {

	List<RiskMonitor> paymentprocess();
	
	void processRules();
	
	List<Rules> getRules();
}
