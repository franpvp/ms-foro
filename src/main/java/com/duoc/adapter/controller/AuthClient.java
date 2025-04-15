package com.duoc.adapter.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "auth-service", url = "http://spring-gestion-usuarios:8081/api/v1/auth")
public interface AuthClient {

    // Verifica si el usuario está autenticado
    @GetMapping("/estado/{id-usuario}")
    ResponseEntity<Boolean> verificarEstadoUsuario(@PathVariable("id-usuario") Long idUsuario);
}
