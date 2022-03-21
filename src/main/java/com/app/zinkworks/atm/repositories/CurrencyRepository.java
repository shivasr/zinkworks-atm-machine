package com.app.zinkworks.atm.repositories;

import com.app.zinkworks.atm.domain.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

/**
 *Repository to retrieve and save currency notes.
 */
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, UUID> {
 List<CurrencyEntity> findAll();

 CurrencyEntity save(CurrencyEntity currencyEntity);
}
