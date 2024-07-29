package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.entity.Client;
import org.example.dienluc.repository.ClientRepository;
import org.example.dienluc.service.ClientService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.UpdateEmailRequest;
import org.example.dienluc.service.dto.UpdateEmailResponse;
import org.example.dienluc.service.dto.client.ClientCreateDto;
import org.example.dienluc.service.dto.client.ClientGetDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
