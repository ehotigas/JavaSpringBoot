package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.example.demo.controller.dto.AutenticarUsuarioDto;
import com.example.demo.controller.dto.CriarUsuarioDto;
import com.example.demo.model.entity.Usuario;
import com.example.demo.service.IUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Operation(description = "Retorna a string 'Hello World'")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a string"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    
    @Autowired
    private IUsuarioService service;

    @Operation(description = "Salva um usuário e o retorna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody CriarUsuarioDto input) {
        Usuario usuario = Usuario.builder().nome(input.getNome()).email(input.getEmail()).senha(input.getSenha()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarUsuario(usuario));
    }


    @Operation(description = "Autenticar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticar(@RequestBody AutenticarUsuarioDto input) {
        Usuario usuario = service.autenticar(input.getEmail(), input.getSenha());
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
}
