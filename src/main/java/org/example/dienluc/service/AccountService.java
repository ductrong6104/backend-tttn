package org.example.dienluc.service;

import org.example.dienluc.entity.Account;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.account.AccountCreateDto;
import org.example.dienluc.service.dto.account.AccountGetDto;
import org.example.dienluc.service.dto.account.AccountNewDto;
import org.example.dienluc.service.dto.account.AccountSiginDto;

import java.util.List;

public interface AccountService {
    public AccountNewDto createAccount(AccountCreateDto accountCreateDto);

    public ResponseData login(AccountSiginDto accountSiginDto);

    public List<AccountGetDto> getAccountOfEmployees();

    public String deleteAccount(String username);
}
