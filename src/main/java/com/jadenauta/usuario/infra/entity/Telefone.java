package com.jadenauta.usuario.infra.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telefone")
@Builder
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero", length = 50)
    private String numero;
    @Column(name = "DDD", length = 3)
    private String ddd;
    @Column(name = "usuario_id")
    private Long usuario_id;


}
