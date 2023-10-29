package com.dio.padroes.service;

import com.dio.padroes.model.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();
    Cliente findById(Long id);
    void save(Cliente cliente);
    void udpate(Long id, Cliente cliente);
    void deleteById(Long id);
}
