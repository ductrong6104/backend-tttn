package org.example.dienluc.service;

import org.example.dienluc.entity.Client;
import org.example.dienluc.service.dto.*;
import org.example.dienluc.service.dto.client.ClientCreateDto;
import org.example.dienluc.service.dto.client.ClientGetDto;
import org.example.dienluc.service.dto.client.ClientGetInforDto;

import java.util.List;

public interface ClientService {
    public Client createClient(ClientCreateDto clientCreateDto);

    public ResponseCheck checkEmailOfClientExists(String email);

    public ResponseCheck checkPhoneOfClientExists(String phone);

    public ResponseCheck checkIdentityCardOfClientExists(String identityCard);

    public UpdateEmailResponse updateEmailClient(Integer clientId, String email);

    public ClientGetDto getClientById(Integer clientId);

    public List<ClientGetInforDto> getAllClient();

    public UpdatePhoneResponse updatePhoneClient(Integer clientId, UpdatePhoneRequest updatePhoneRequest);

    public UpdateIdentityCardResponse updateIdentityCard(Integer clientId, UpdateIdentityCard updateIdentityCard);

    public UpdateBirthdayResponse updateBirthday(Integer clientId, UpdateBirthdayRequest updateBirthday);

    public UpdateAddressResponse updateAddress(Integer clientId, UpdateAddressRequest updateAddress);
}
