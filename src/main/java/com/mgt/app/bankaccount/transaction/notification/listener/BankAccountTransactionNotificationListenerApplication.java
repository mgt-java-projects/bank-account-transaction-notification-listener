package com.mgt.app.bankaccount.transaction.notification.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.mgt.app.bank.account.transaction.event.persist.config.AccountTransactionEventPersistConfig;

/**
 * Boot strap class the bank account transaction notification listener
 * Application.
 * 
 * @author stami
 *
 */
@EnableConfigurationProperties
@SpringBootApplication
@Import(AccountTransactionEventPersistConfig.class)
public class BankAccountTransactionNotificationListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountTransactionNotificationListenerApplication.class, args);
	}

}
