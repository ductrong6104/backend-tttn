package org.example.dienluc.service;

import org.example.dienluc.entity.Client;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.UpdateEmailRequest;
import org.example.dienluc.service.dto.UpdateEmailResponse;
import org.example.dienluc.service.dto.client.ClientCreateDto;
import org.example.dienluc.service.dto.client.ClientGetDto;

public interface ClientService {
    public Client createClient(ClientCreateDto clientCreateDto);

    public ResponseCheck checkEmailOfClientExists(String email);

    public ResponseCheck checkPhoneOfClientExists(String phone);

    public ResponseCheck checkIdentityCardOfClientExists(String identityCard);

    public UpdateEmailResponse updateEmailClient(Integer clientId, String email);

    public ClientGetDto getClientById(Integer clientId);
}
