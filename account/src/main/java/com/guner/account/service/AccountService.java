package com.guner.account.service;

import com.guner.account.dto.AccountDto;
import com.guner.account.dto.converter.AccountDtoConverter;
import com.guner.account.dto.request.CreateAccountRequest;
import com.guner.account.model.Account;
import com.guner.account.model.Customer;
import com.guner.account.model.Transaction;
import com.guner.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class AccountService {

    //Autowired kullanırsak immutable olmuyor
    //Java 7 den sonra artık Service katmanında Interface kullanmaya gerek yok
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final Clock clock;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, Clock clock, AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.clock = clock;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
               LocalDateTime.now());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
           Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
           account.getTransaction().add(transaction);
        }
        return converter.convert(accountRepository.save(account));
    }

    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }

}
