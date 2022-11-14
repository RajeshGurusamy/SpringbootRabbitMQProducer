package com.persistent.rabbitmq.producer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.rabbitmq.producer.model.RiskMonitor;
import com.persistent.rabbitmq.producer.model.Rules;
import com.persistent.rabbitmq.producer.repository.PaymentProcessRepository;
import com.persistent.rabbitmq.producer.repository.ProcessRuleRepository;


@Service
public class PaymentProcessServiceImpl implements PaymentProcessService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PaymentProcessRepository paymentprocessrepository;

	@Autowired
	private ProcessRuleRepository processRuleRepository;

	public PaymentProcessServiceImpl(PaymentProcessRepository paymentprocessrepository,
			ProcessRuleRepository processRuleRepository) {
		super();
		this.paymentprocessrepository = paymentprocessrepository;
		this.processRuleRepository = processRuleRepository;
	}

	@Override
	public List<RiskMonitor> paymentprocess() {
		System.out.println("paymentprocess "+paymentprocessrepository.findAll());
		return paymentprocessrepository.findAll() != null?paymentprocessrepository.findAll():null;
		
	}
	
	@Override
	public void processRules() {
		processRuleRepository.save(new Rules("1", "rule1", "rule \"Throttle Value based rule\"\n"
				+ "	when\n"
				+ "		TransactionDetails(throttleValue)\n"
				+ "	then\n"
				+ "		transactionStatus.addOnHoldReason(\"Fed level throttling is On\")\n"
				+ "end", "10/26/2022"));
		processRuleRepository.save(new Rules("2", "rule2", "rule \"Dynamic Amount based rule\"\n"
				+ "	when\n"
				+ "		c : TransactionDetails(debitAmt > dynamicAmount)\n"
				+ "	then\n"
				+ "		transactionStatus.addOnHoldReason(\"Debit Amount is greater than Threshold: $\" + c.getDynamicAmount)\n"
				+ "end", "10/26/2022"));
		processRuleRepository.save(new Rules("3", "rule3", "rule \"On Hold based rule\"\n"
				+ "	when\n"
				+ "		TransactionDetails(throttleValue || nm==\"CITI\" || pmtRail==\"RTL\" || throttleValue || debitAmt > dynamicAmount || debitAmt > (cap*throttleMaxAvailable))\n"
				+ "	then\n"
				+ "		transactionStatus.setStatusOnHold(true)\n"
				+ "end", "10/26/2022"));
		
	}

	@Override
	public List<Rules> getRules() {
		
		return processRuleRepository.findAll();
	}

}
