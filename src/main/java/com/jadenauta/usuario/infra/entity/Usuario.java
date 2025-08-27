package com.jadenauta.usuario.infra.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter // o lombok ajuda a pegar os getters e os setters
@Setter
@AllArgsConstructor // o lombok criar os construtores com Allargs para todos as variaveis.
@NoArgsConstructor// o lombok cria construtores vazios para polimosfismo.
// Falando para o Spring quem é a entidade e o nome dela para cadastrar na tabela no banco
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nome",length=50)
    private String nome;
    @Column(name =" email", length=50)
    private String email;
    @Column(name = "senha")
    private String senha;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> endereco;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }


}
