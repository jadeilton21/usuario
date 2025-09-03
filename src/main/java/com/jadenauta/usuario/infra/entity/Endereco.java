package com.jadenauta.usuario.infra.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "endereco")
@Builder
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="logradouro", length = 50)
    private String logradouro;
    @Column(name ="numero", length = 50)
    private String numero;
    @Column(name ="complemento", length = 50)
    private String complemento;
    @Column(name ="bairro", length = 50)
    private String bairro;
    @Column(name ="cidade", length = 50)
    private String cidade;
    @Column(name ="estado", length = 50)
    private String estado;
    @Column(name ="cep", length = 50)
    private String cep;
    @Column(name ="uf", length = 50)
    private String uf;
    @Column(name = "usuario_id")
    private Long usuario_id;




}
