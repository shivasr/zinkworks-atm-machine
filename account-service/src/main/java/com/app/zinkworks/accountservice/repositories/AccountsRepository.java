package com.app.zinkworks.accountservice.repositories;

import com.app.zinkworks.accountservice.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountsRepository extends JpaRepository<AccountEntity, UUID> {
    AccountEntity findAccountEntityByAccountNumber(String accountNumber);

    AccountEntity save(AccountEntity accountEntity);
}
