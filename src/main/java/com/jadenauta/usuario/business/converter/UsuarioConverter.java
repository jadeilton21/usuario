package com.jadenauta.usuario.business.converter;

import com.jadenauta.usuario.business.dtos.EnderecoDTO;
import com.jadenauta.usuario.business.dtos.TelefoneDTO;
import com.jadenauta.usuario.business.dtos.UsuarioDTO;
import com.jadenauta.usuario.infra.entity.Endereco;
import com.jadenauta.usuario.infra.entity.Telefone;
import com.jadenauta.usuario.infra.entity.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
public class UsuarioConverter {




    public UsuarioConverter(RestClient.Builder builder) {
    }

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {

        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(paraListaEndereco(usuarioDTO.getEndereco()))

                .telefone(paraListaTelefone(usuarioDTO.getTelefone()))

                .build();

    }





    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }


    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {

        return Endereco.builder()
                .numero(enderecoDTO.getNumero())
                .cep(enderecoDTO.getCep())
                .uf(enderecoDTO.getUf())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .bairro(enderecoDTO.getBairro())
                .logradouro(enderecoDTO.getLogradouro())
                .build();
    }


    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {

        return telefoneDTOS.stream().map(this::paraTelefone).toList();

    }


    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }



    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {

        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(paraListaEnderecoDTO(usuarioDTO.getEndereco()))

                .telefone(paraListaTelefoneDTO(usuarioDTO.getTelefone()))

                .build();

    }





    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }


    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {

        return EnderecoDTO.builder()
                .id(endereco.getId())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .build();
    }


    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS) {

        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();

    }


    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }


    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {


        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .build();




    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {

        return Endereco.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getComplemento() : entity.getComplemento())
                .uf(dto.getUf() != null ? dto.getUf() : entity.getUf())
                .bairro(dto.getBairro() != null ? dto.getBairro() : entity.getBairro())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .logradouro(dto.getLogradouro() != null ? dto.getLogradouro() : entity.getLogradouro())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cidade(dto.getCidade() != null ? dto.getCidade(): entity.getCidade())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO  dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? entity.getNumero() : dto.getNumero())
                .ddd(dto.getDdd() != null ? entity.getDdd() : dto.getDdd())
                .build();
    }


    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario) {

        return Endereco.builder()
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .logradouro(dto.getLogradouro())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .uf(dto.getUf())
                .bairro(dto.getBairro())
                .usuario_id(idUsuario)
                .build();
    }



    public Telefone paraTelefone(TelefoneDTO dto, Long idUsuario) {


        return Telefone.builder()

                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario)
                .build();
    }




}
