package com.persistent.rabbitmq.producer.controller;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.persistent.rabbitmq.producer.model.Payment;
import com.persistent.rabbitmq.producer.model.RiskMonitor;
import com.persistent.rabbitmq.producer.model.Rules;
import com.persistent.rabbitmq.producer.service.PaymentProcessServiceImpl;

@RestController
@RequestMapping(path="payment")
@ControllerAdvice
public class PaymentProcessMQController {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	private PaymentProcessServiceImpl paymentProcessServiceImpl;

	@Autowired
	private Payment payment;
	
	@Value("${payment.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${payment.rabbitmq.routingkey}")
	private String routingKey;
	
	@GetMapping("/v1/producer/{exchangeName}/{routingKey}/{messageData}")
	public String producer(@PathVariable String exchangeName, @PathVariable String routingKey, @PathVariable String messageData) throws JsonProcessingException, InterruptedException {
		
		payment.setPaymentId("Credit101");
		payment.setPaymentDescriptions("This is credit card payment transactions");
		System.out.println("payment    --->"+payment.toString());
		List<RiskMonitor> processdPayments = paymentProcessServiceImpl.paymentprocess();
		System.out.println(processdPayments.toString());
		
		amqpTemplate.convertAndSend(exchangeName,routingKey, payment.toString());
		return "Message sent to RabbitMQ successfully";
	}
	
	@GetMapping("/v1/rules")
	public List<Rules> getRules() throws JsonProcessingException, InterruptedException {
		
		paymentProcessServiceImpl.processRules();
		List<Rules> ruleList = paymentProcessServiceImpl.getRules();
		for(Rules rule: ruleList) {
			System.out.println("--------------Rules-----------------");
			System.out.println(rule.getRule_id());
			System.out.println(rule.getRule_description());
			
		}
		
		return ruleList;
	}
	
	@PostMapping("v1/process")
	public String processPayments(@RequestBody Payment payment){
		
		payment.setPaymentId(payment.getPaymentId());
		payment.setPaymentDescriptions(payment.getPaymentDescriptions());
		System.out.println("TopicExchange ::"+exchange);
		System.out.println("Routing Key ::"+routingKey);
		System.out.println("payment ::"+payment.toString());
		amqpTemplate.convertAndSend(exchange,routingKey, payment.toString());
		
		return "Rabbit MQ processed successfully";
	}

}
