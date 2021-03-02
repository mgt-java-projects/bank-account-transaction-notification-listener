package com.mgt.app.bankaccount.transaction.notification.listener.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgt.app.bank.account.transaction.event.common.dto.AccountTransactionDTO;
import com.mgt.app.bank.account.transaction.event.persist.repository.service.AccountTransactionRepositoryService;

/**
 * Kakfa listener service to listen bank account transaction notification
 * events.
 * 
 * @author stami
 *
 */

@EnableKafka
@Service
public class AccountTransactionEventListener {

	/**
	 * Instance of Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(AccountTransactionEventListener.class);

	/**
	 * Instance of {@link ObjectMapper}
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Bean instance of {@link AccountTransactionRepositoryService}
	 */
	@Autowired
	private AccountTransactionRepositoryService actTransRepoService;

	/**
	 * Listen message events posted in configured kafka topic, consumer group and
	 * store into persist db.
	 * 
	 * @param consumerRecord
	 */
	@KafkaListener(topics = "${bank.account.transaction.event.consumer.topic.config.name}", groupId = "${bank.account.transaction.event.consumer.topic.config.consumer-group-id}")
	public void listenAccountTransactionEvent(ConsumerRecord<String, String> consumerRecord) {
		log.debug("In listenAccountTransactionEvent...");
		log.info("listener is proessing transaction event for offset" + consumerRecord.offset());
		try {
			AccountTransactionDTO accountTransaction = objectMapper.readValue(consumerRecord.value(),
					AccountTransactionDTO.class);
			actTransRepoService.addNewAccountTransaction(accountTransaction);
			log.debug("Out listenAccountTransactionEvent...");
		} catch (JsonProcessingException exp) {
			log.error("Exception occured when processing event at listenAccountTransactionEvent", exp);
		}
	}

}
