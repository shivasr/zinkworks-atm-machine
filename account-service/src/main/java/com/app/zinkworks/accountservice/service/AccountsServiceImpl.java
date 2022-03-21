package com.app.zinkworks.accountservice.service;

import com.app.zinkworks.accountservice.AccountServiceApplication;
import com.app.zinkworks.accountservice.domain.AccountEntity;
import com.app.zinkworks.accountservice.exceptions.AccountsServiceException;
import com.app.zinkworks.accountservice.repositories.AccountsRepository;
import com.app.zinkworks.accountservice.web.models.AccountBalance;
import com.app.zinkworks.accountservice.web.models.TransactionDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public boolean validatePin(String accountNumber, String pin) {
        AccountEntity accountEntity = accountsRepository.findAccountEntityByAccountNumber(accountNumber);

        if(accountEntity.getPin().equalsIgnoreCase(pin))
            return true;
        return false;
    }

    @Override
    public AccountBalance getAccountBalance(String accountNumber) {
        AccountEntity accountEntity = accountsRepository.findAccountEntityByAccountNumber(accountNumber);

        return AccountBalance.builder()
                    .accountNumber(accountNumber)
                    .balance(accountEntity.getBalance().toPlainString())
                    .overdraft(accountEntity.getOverdraft().toPlainString())
                .returnCode("00")
                .message("Transaction successful.")
                .build();
    }

    @Override
    @Transactional
    public TransactionDetails withdrawAmount(String accountNumber, BigDecimal amount) throws AccountsServiceException {
        AccountEntity accountEntity = accountsRepository.findAccountEntityByAccountNumber(accountNumber);
        Optional.ofNullable(accountEntity).orElseThrow(
                () -> new AccountsServiceException("02",
                        String.format("Account with number {} does not exist.", accountNumber)));


        if(accountEntity.getBalance().add(accountEntity.getOverdraft()).compareTo(amount) < 0)
            throw new AccountsServiceException("03", "You have insufficient balance.");

        BigDecimal prevBalance = accountEntity.getBalance();
        BigDecimal newBalance = accountEntity.getBalance();
        BigDecimal prevOverdraftBalance = accountEntity.getOverdraft();
        BigDecimal newOverdraftBalance = accountEntity.getOverdraft();

        if(prevBalance.compareTo(amount) >= 0) {
            newBalance = prevBalance.subtract(amount);
        } else {
            newOverdraftBalance = prevOverdraftBalance.subtract(amount);
        }
        accountEntity.setBalance(newBalance);
        accountEntity.setOverdraft(newOverdraftBalance);
        accountsRepository.save(accountEntity);

        return TransactionDetails.builder()
                .accountNumber(accountNumber)
                .prevOverdraft(prevOverdraftBalance.toPlainString())
                .prevBalance(prevBalance.toPlainString())
                .newOverdraft(newOverdraftBalance.toPlainString())
                .newBalance(newBalance.toPlainString())
                .withdrawAmount(amount.toPlainString())
                .returnCode("00")
                .message("Transaction successful.")
                .build();
    }
}
