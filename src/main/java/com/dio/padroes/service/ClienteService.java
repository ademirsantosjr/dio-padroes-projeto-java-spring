package com.dio.padroes.service;

import com.dio.padroes.model.Cliente;
import com.dio.padroes.model.Endereco;
import com.dio.padroes.repository.ClienteRepository;
import com.dio.padroes.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final IViaCepService iViaCePService;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                                                                "Cliente not found"));
    }

    @Override
    public void save(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep)
                  .orElseGet(() -> {
                      Endereco newEndereco = iViaCePService.findCep(cep);
                      return enderecoRepository.save(newEndereco);
                  });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void udpate(Long id, Cliente cliente) {

        Cliente foundCLiente = clienteRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                                                                "Cliente not found"));

        String cep = cliente.getEndereco().getCep();

        Endereco endereco = enderecoRepository.findById(cep)
              .orElseGet(() -> {
                  Endereco newEndereco = iViaCePService.findCep(cep);
                  return enderecoRepository.save(newEndereco);
              });

        cliente.setEndereco(endereco);

        cliente.setId(foundCLiente.getId());

        clienteRepository.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
