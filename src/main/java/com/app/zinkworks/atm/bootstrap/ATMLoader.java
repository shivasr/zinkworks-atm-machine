package com.app.zinkworks.atm.bootstrap;

import com.app.zinkworks.atm.domain.CurrencyEntity;
import com.app.zinkworks.atm.repositories.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ATMLoader implements CommandLineRunner {

    private final CurrencyRepository currencyRepository;

    public ATMLoader(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadATM();
    }

    /**
     * Loads the ATM on the startup
     */
    private void loadATM() {
        if(currencyRepository.count() == 0) {
            currencyRepository.save(CurrencyEntity.builder()
                    .number(10)
                    .currency(50)
                    .build());

            currencyRepository.save(CurrencyEntity.builder()
                    .number(30)
                    .currency(20)
                    .build());

            currencyRepository.save(CurrencyEntity.builder()
                    .number(30)
                    .currency(10)
                    .build());

            currencyRepository.save(CurrencyEntity.builder()
                    .number(20)
                    .currency(5)
                    .build());
        }
    }
}
