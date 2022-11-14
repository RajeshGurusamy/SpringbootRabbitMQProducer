package com.persistent.rabbitmq.producer.model;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
@Transactional
@Document(collection = "Rules")
public class Rules implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String _id;
	private String rule_id;
	private String rule_description;
	private String date;
	 
	@Override
	public String toString() {
		return "Rules [_id=" + _id + ", rule_id=" + rule_id + ", rule_description=" + rule_description + ", date=" + date
				+ "]";
	}

}
