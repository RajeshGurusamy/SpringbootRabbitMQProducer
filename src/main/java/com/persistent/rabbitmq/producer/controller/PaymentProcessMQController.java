package com.persistent.rabbitmq.producer.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.persistent.rabbitmq.producer.model.Payment;

@RestController
@RequestMapping(path="payment")
@ControllerAdvice
public class PaymentProcessMQController {
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@GetMapping("/v1/producer/{exchangeName}/{routingKey}/{messageData}")
	public String producer(@PathVariable String exchangeName, @PathVariable String routingKey, @PathVariable String messageData) throws JsonProcessingException, InterruptedException {
		
		Payment payment = new Payment();
		payment.setPaymentId("Credit101");
		payment.setPaymentDescriptions("This is credit card payment transactions");
		amqpTemplate.convertAndSend(exchangeName,routingKey, payment.toString());
		return "Message sent to RabbitMQ successfully";
	}

}
