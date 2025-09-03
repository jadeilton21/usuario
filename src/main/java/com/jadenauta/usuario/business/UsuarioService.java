package com.jadenauta.usuario.business;


import com.jadenauta.usuario.business.converter.UsuarioConverter;
import com.jadenauta.usuario.business.dtos.EnderecoDTO;
import com.jadenauta.usuario.business.dtos.TelefoneDTO;
import com.jadenauta.usuario.business.dtos.UsuarioDTO;
import com.jadenauta.usuario.infra.entity.Endereco;
import com.jadenauta.usuario.infra.entity.Telefone;
import com.jadenauta.usuario.infra.entity.Usuario;
import com.jadenauta.usuario.infra.exceptions.ConflictException;
import com.jadenauta.usuario.infra.exceptions.ResourceNotFoundException;
import com.jadenauta.usuario.infra.repository.EnderecoRepository;
import com.jadenauta.usuario.infra.repository.TelefoneRepository;
import com.jadenauta.usuario.infra.repository.UsuarioRepository;
import com.jadenauta.usuario.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public void emailExiste(String email) {

        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já Cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Esse email já existe", e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
       try {
            return usuarioConverter.paraUsuarioDTO(
                   usuarioRepository.findByEmail(email)
                           .orElseThrow(
                   () -> new ResourceNotFoundException("Email não encontrado" + email)
                           )
           );
       }catch (ResourceNotFoundException e ){
           throw new ResourceNotFoundException("Enmail não Encontrado: " + email);
       }
    }

    public void deletarEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuari(String token, UsuarioDTO dto) {
        // Aqui busca o email do usuario através do token ( Tirar a obrigatoriedade do email)
        String email = jwtUtil.extraiEmailDoToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null? passwordEncoder.encode(dto.getSenha()) : null);

        // busca os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não Localizado!!"));


        //mesclou os dados que recebemos na requisição DTO com os dados do banco de dados.
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // critografica a senha
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        // salva os dados convertidos do usuario.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado" + idEndereco));


        Endereco endereco = usuarioConverter.updateEndereco(dto, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }



    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {


        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não Encontrado: "  + idTelefone));


        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }


    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
        String email = jwtUtil.extraiEmailDoToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não Localizado" + email));


        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }



    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
        String email = jwtUtil.extraiEmailDoToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Telefone não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefone(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }


}

