package com.dio.padroes.service;

import com.dio.padroes.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface IViaCepService {

    @GetMapping("/{cep}/json/")
    Endereco findCep(@PathVariable String cep);
}
