package com.jadenauta.usuario.controller;


import com.jadenauta.usuario.business.UsuarioService;
import com.jadenauta.usuario.business.dtos.UsuarioDTO;
import com.jadenauta.usuario.infra.entity.Usuario;
import com.jadenauta.usuario.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }


    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return "Bearer: " + jwtUtil.generateToken(authentication.getName());

    }
    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam("email") String email) {

        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));

    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarEmail(email);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuari(token, dto));
    }


}
