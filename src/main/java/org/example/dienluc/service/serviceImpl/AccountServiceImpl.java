
package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Role;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.repository.AccountRepository;
import org.example.dienluc.repository.RoleRepository;
import org.example.dienluc.service.AccountService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.account.AccountCreateDto;
import org.example.dienluc.service.dto.account.AccountNewDto;
import org.example.dienluc.service.dto.account.AccountSiginDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public AccountNewDto createAccount(AccountCreateDto accountCreateDto) {
        Account account = modelMapper.map(accountCreateDto, Account.class);
        return modelMapper.map(accountRepository.save(account), AccountNewDto.class);
    }

    @Override
    public ResponseData login(AccountSiginDto accountSiginDto) {
        ResponseData responseData = new ResponseData();
        Optional<Account> optionalAccount = accountRepository.findByUsername(accountSiginDto.getUsername());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getPassword().equals(accountSiginDto.getPassword())){
                if (account.getRole().getId().equals(accountSiginDto.getRoleId()) && account.getDisabled().equals(false)){
                    AccountNewDto accountNewDto = modelMapper.map(account, AccountNewDto.class);
                    if (accountSiginDto.getRoleId().equals(3))
                        accountNewDto.setClientId(account.getClients().isEmpty() ? null: account.getClients().get(0).getId());
                    else
                        accountNewDto.setClientId(account.getEmployees().isEmpty() ? null: account.getEmployees().get(0).getId());
                    responseData.setData(accountNewDto);
                    responseData.setStatus(HttpStatus.OK.value());
                    responseData.setMessage("Login success");
                }
                else if (accountSiginDto.getRoleId().equals(3)){
                    responseData.setStatus(703);
                    responseData.setMessage("This account is not for client");
                }
                else if (accountSiginDto.getRoleId().equals(1)){
                    responseData.setStatus(704);
                    responseData.setMessage("This account is not for manager");
                }
                else if (accountSiginDto.getRoleId().equals(2)){
                    responseData.setStatus(705);
                    responseData.setMessage("This account is not for employee");
                }
            }
            else{
                responseData.setStatus(702);
                responseData.setMessage("Password incorrect");
            }

        }
        else{
            responseData.setStatus(701);
            responseData.setMessage("Username is not registered");
        }
        return responseData;
    }
}
