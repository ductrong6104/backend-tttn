package org.example.dienluc.controller;

import jakarta.validation.Valid;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ClientService;
import org.example.dienluc.service.dto.*;
import org.example.dienluc.service.dto.client.ClientCreateDto;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * REST controller for managing clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * {@code POST /accounts}  : Creates a new client.
     * Creates a new client if the phone, identityCard, email are not already in use.
     *
     * @param clientCreateDto the user to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body type {@link ResponseData} containing status {@code 201 (Created)} and with body containing data of type {@link org.example.dienluc.entity.Client}.
     * @throws DataIntegrityViolationException with body containing status {@code 400 (Bad Request)} if the phone or identityCard or email is already in use.
     * @see org.example.dienluc.errors.GlobalExceptionHandler#handleDataIntegrityViolationException(DataIntegrityViolationException, WebRequest)
     */
    @PostMapping("")
    public ResponseEntity<?> createClient(@RequestBody ClientCreateDto clientCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(clientService.createClient(clientCreateDto))
                .message("create new client")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    /**
     * {@code GET /accounts/check-email}  : Check email has been used
     *
     * @param updateEmailRequest email entered by the user.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body type {@link ResponseData} containing status {@code 200 (OK)} and with body containing data of type {@link ResponseCheck}.
     * @throws DataIntegrityViolationException with body containing status {@code 400 (Bad Request)} if the phone or identityCard or email is already in use.
     * @see org.example.dienluc.errors.GlobalExceptionHandler#handleDataIntegrityViolationException(DataIntegrityViolationException, WebRequest)
     */
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmailOfClientExists(@Valid @RequestBody UpdateEmailRequest updateEmailRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(clientService.checkEmailOfClientExists(updateEmailRequest.getEmail()))
                .message("check email of client exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhoneOfClientExists(@RequestParam(name = "phone") String phone) {
        ResponseData responseData = ResponseData.builder()
                .data(clientService.checkPhoneOfClientExists(phone))
                .message("check email of client exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/check-identityCard")
    public ResponseEntity<?> checkIdentityCardOfClientExists(@RequestParam(name = "identityCard") String identityCard) {
        ResponseData responseData = ResponseData.builder()
                .data(clientService.checkIdentityCardOfClientExists(identityCard))
                .message("check email of client exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{clientId}/email")
    public ResponseEntity<?> updateEmailClient(@PathVariable Integer clientId, @RequestBody UpdateEmailRequest updateEmailRequest){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.updateEmailClient(clientId, updateEmailRequest.getEmail()))
                .message("update email client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{clientId}/phone")
    public ResponseEntity<?> updatePhoneClient(@PathVariable Integer clientId, @RequestBody UpdatePhoneRequest updatePhoneRequest){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.updatePhoneClient(clientId, updatePhoneRequest))
                .message("update phone client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{clientId}/identityCard")
    public ResponseEntity<?> updateIdentityCard(@PathVariable Integer clientId, @RequestBody UpdateIdentityCard updateIdentityCard){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.updateIdentityCard(clientId, updateIdentityCard))
                .message("update identity client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{clientId}/birthday")
    public ResponseEntity<?> updateBirthday(@PathVariable Integer clientId, @RequestBody UpdateBirthdayRequest updateBirthday){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.updateBirthday(clientId, updateBirthday))
                .message("update birthday client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{clientId}/address")
    public ResponseEntity<?> updateAddress(@PathVariable Integer clientId, @RequestBody UpdateAddressRequest updateAddress){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.updateAddress(clientId, updateAddress))
                .message("update address client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable Integer clientId){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.getClientById(clientId))
                .message("get client by id")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllClient(){
        ResponseData responseData = ResponseData.builder()
                .data(clientService.getAllClient())
                .message("get all client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
