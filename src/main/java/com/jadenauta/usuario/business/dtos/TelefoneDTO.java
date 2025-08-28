package com.jadenauta.usuario.business.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefoneDTO {


    private Long id;
    private String numero;
    private String ddd;
}
