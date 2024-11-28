package com.assadosCombate.mappers;

import com.assadosCombate.dtos.entities.UsuarioDTO;
import com.assadosCombate.entities.Usuario;
import com.assadosCombate.enums.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T12:46:46-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( usuarioDTO.getId() );
        usuario.setNome( usuarioDTO.getNome() );
        usuario.setLogin( usuarioDTO.getLogin() );
        usuario.setEmail( usuarioDTO.getEmail() );
        usuario.setPassword( usuarioDTO.getPassword() );
        usuario.setStatus( usuarioDTO.getStatus() );
        if ( usuarioDTO.getRole() != null ) {
            usuario.setRole( Enum.valueOf( UserRole.class, usuarioDTO.getRole() ) );
        }

        return usuario;
    }

    @Override
    public UsuarioDTO toDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId( usuario.getId() );
        usuarioDTO.setNome( usuario.getNome() );
        usuarioDTO.setPassword( usuario.getPassword() );
        usuarioDTO.setLogin( usuario.getLogin() );
        usuarioDTO.setEmail( usuario.getEmail() );
        if ( usuario.getRole() != null ) {
            usuarioDTO.setRole( usuario.getRole().name() );
        }
        usuarioDTO.setStatus( usuario.getStatus() );

        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> toDto(List<Usuario> usuario) {
        if ( usuario == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( usuario.size() );
        for ( Usuario usuario1 : usuario ) {
            list.add( toDto( usuario1 ) );
        }

        return list;
    }

    @Override
    public Usuario partialUpdate(UsuarioDTO usuarioDTO, Usuario usuario) {
        if ( usuarioDTO == null ) {
            return usuario;
        }

        if ( usuarioDTO.getId() != null ) {
            usuario.setId( usuarioDTO.getId() );
        }
        if ( usuarioDTO.getNome() != null ) {
            usuario.setNome( usuarioDTO.getNome() );
        }
        if ( usuarioDTO.getLogin() != null ) {
            usuario.setLogin( usuarioDTO.getLogin() );
        }
        if ( usuarioDTO.getEmail() != null ) {
            usuario.setEmail( usuarioDTO.getEmail() );
        }
        if ( usuarioDTO.getPassword() != null ) {
            usuario.setPassword( usuarioDTO.getPassword() );
        }
        if ( usuarioDTO.getStatus() != null ) {
            usuario.setStatus( usuarioDTO.getStatus() );
        }
        if ( usuarioDTO.getRole() != null ) {
            usuario.setRole( Enum.valueOf( UserRole.class, usuarioDTO.getRole() ) );
        }

        return usuario;
    }
}
