package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.Client;
import org.example.dienluc.repository.ClientRepository;
import org.example.dienluc.service.ClientService;
import org.example.dienluc.service.dto.*;
import org.example.dienluc.service.dto.client.ClientCreateDto;
import org.example.dienluc.service.dto.client.ClientGetDto;
import org.example.dienluc.service.dto.client.ClientGetInforDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Client createClient(ClientCreateDto clientCreateDto) {
        return clientRepository.save(modelMapper.map(clientCreateDto, Client.class));
    }

    @Override
    public ResponseCheck checkEmailOfClientExists(String email) {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }

    @Override
    public ResponseCheck checkPhoneOfClientExists(String phone) {
        Optional<Client> optionalClient = clientRepository.findByPhone(phone);
        if (optionalClient.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }

    @Override
    public ResponseCheck checkIdentityCardOfClientExists(String identityCard) {
        Optional<Client> optionalClient = clientRepository.findByIdentityCard(identityCard);
        if (optionalClient.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }

    @Override
    public UpdateEmailResponse updateEmailClient(Integer clientId, String email) {
        Client client = clientRepository.findById(clientId).get();
        client.setEmail(email);
        return modelMapper.map(clientRepository.save(client), UpdateEmailResponse.class);
    }

    @Override
    public ClientGetDto getClientById(Integer clientId) {
        return modelMapper.map(clientRepository.findById(clientId).get(), ClientGetDto.class);
    }

    @Override
    public List<ClientGetInforDto> getAllClient() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientGetInforDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public UpdatePhoneResponse updatePhoneClient(Integer clientId, UpdatePhoneRequest updatePhoneRequest) {
        Client client = clientRepository.findById(clientId)
                        .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        client.setPhone(updatePhoneRequest.getPhone());
        return modelMapper.map(clientRepository.save(client), UpdatePhoneResponse.class);
    }

    @Override
    public UpdateIdentityCardResponse updateIdentityCard(Integer clientId, UpdateIdentityCard updateIdentityCard) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        client.setIdentityCard(updateIdentityCard.getIdentityCard());
        return modelMapper.map(clientRepository.save(client), UpdateIdentityCardResponse.class);
    }

    @Override
    public UpdateBirthdayResponse updateBirthday(Integer clientId, UpdateBirthdayRequest updateBirthday) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        client.setBirthday(updateBirthday.getBirthday());
        return modelMapper.map(clientRepository.save(client), UpdateBirthdayResponse.class);
    }

    @Override
    public UpdateAddressResponse updateAddress(Integer clientId, UpdateAddressRequest updateAddress) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        client.setAddress(updateAddress.getAddress());
        return modelMapper.map(clientRepository.save(client), UpdateAddressResponse.class);
    }
}
