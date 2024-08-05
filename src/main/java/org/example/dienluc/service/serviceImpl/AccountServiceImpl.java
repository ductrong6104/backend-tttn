
package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.Role;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.repository.AccountRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.repository.RoleRepository;
import org.example.dienluc.service.AccountService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.account.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
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
            if (account.getDisabled().equals(false)){

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
                responseData.setStatus(706);
                responseData.setMessage("This account is disabled");
            }

        }
        else{
            responseData.setStatus(701);
            responseData.setMessage("Username is not registered");
        }
        return responseData;
    }

    @Override
    public List<AccountGetDto> getAccountOfEmployees() {
        Role role = roleRepository.findByName("Nhân viên")
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name: " + "Nhân viên"));

        return accountRepository.findByRole(role).stream()
                .map(account ->{
                    AccountGetDto accountGetDto = modelMapper.map(account, AccountGetDto.class);
                    accountGetDto.setPassword("********");
//                    if (!account.getEmployees().isEmpty())
//                        accountGetDto.setEmployeeIdAndName(account.getEmployees().get(0).getIdAndFullName());
                    return accountGetDto;
                } )
                .collect(Collectors.toList());

    }

    @Transactional
    @Override
    public String deleteAccount(String username) {
        if (accountRepository.existsByUsername(username)) {
            accountRepository.deleteByUsername(username);
            return "Xóa tài khoản thành công";
        } else {
            throw new EntityNotFoundException("Account not found with username: " + username);
        }
    }

    @Override
    public Account updateAccount(Integer accountId, AccountUpdateDto accountUpdateDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name: " + accountId));
        account.setDisabled(accountUpdateDto.getDisabled());
        return accountRepository.save(account);
    }


}
