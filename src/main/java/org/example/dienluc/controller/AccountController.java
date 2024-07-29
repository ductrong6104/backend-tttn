package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.AccountService;
import org.example.dienluc.service.dto.account.AccountCreateDto;
import org.example.dienluc.service.dto.account.AccountSiginDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * REST controller for managing account.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    /**
     * {@code POST /accounts}  : Creates a new account.
     * Creates a new user if the username is not already in use.
     *
     * @param accountCreateDto the user to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body type {@link ResponseData} containing status {@code 201 (Created)} and with body containing data of type {@link org.example.dienluc.service.dto.account.AccountNewDto}.
     * @throws DataIntegrityViolationException with body containing status {@code 400 (Bad Request)} if the username is already in use.
     * @see org.example.dienluc.errors.GlobalExceptionHandler#handleDataIntegrityViolationException(DataIntegrityViolationException, WebRequest)
     */
    @PostMapping("")
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(accountService.createAccount(accountCreateDto))
                .message("create new account")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    /**
     * {@code POST /account/sigin} : user sigin
     * @param accountSiginDto
     * @return {@link ResponseEntity} with status {@code 200} and with body type {@link ResponseData} containing status:
     * <li>{@code 200} if Login is success</li>
     * <li>{@code 701} if Username is not registered</li>
     * <li>{@code 702} if Password incorrect</li>
     * <li>{@code 703} if This account is not for client</li>
     * <li>{@code 704} if This account is not for manager</li>
     * <li>{@code 705} if This account is not for employee</li>
     */
    @PostMapping("/sigin")
    public ResponseEntity<?> login(@RequestBody AccountSiginDto accountSiginDto) {
        return ResponseEntity.ok(accountService.login(accountSiginDto));
    }


}
