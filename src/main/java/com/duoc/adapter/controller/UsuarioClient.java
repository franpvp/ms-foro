package com.duoc.adapter.controller;

import com.duoc.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://spring-gestion-usuarios:8081/api/v1/usuarios")
public interface UsuarioClient {

    // Validamos si el usuario existe en el microservicio que gestiona a los usuarios
    @GetMapping("/{id-usuario}")
    ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable("id-usuario") Long idUsuario);

}
