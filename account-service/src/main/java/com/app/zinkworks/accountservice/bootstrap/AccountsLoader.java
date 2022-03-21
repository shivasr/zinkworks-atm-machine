package com.app.zinkworks.accountservice.bootstrap;

import com.app.zinkworks.accountservice.domain.AccountEntity;
import com.app.zinkworks.accountservice.repositories.AccountsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountsLoader implements CommandLineRunner {

    private final AccountsRepository accountsRepository;

    public AccountsLoader(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        AccountEntity accountEntityOne = AccountEntity.builder()
                .accountNumber("123456789")
                .pin("1234")
                .overdraft(BigDecimal.valueOf(200L))
                .balance(BigDecimal.valueOf(800))
                .build();
        accountsRepository.save(accountEntityOne);

        AccountEntity accountEntityTwo = AccountEntity.builder()
                .accountNumber("987654321")
                .pin("4321")
                .overdraft(BigDecimal.valueOf(150))
                .balance(BigDecimal.valueOf(1230))
                .build();
        accountsRepository.save(accountEntityTwo);


    }
}
