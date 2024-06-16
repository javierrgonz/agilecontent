package com.jrg.techchallenge.infrastructure.rest.spring.mapper;

import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.domain.User.Gender;
import com.jrg.techchallenge.infrastructure.rest.spring.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-23T10:58:42+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
*/
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( user.getUsername() );
        userDto.setName( user.getName() );
        userDto.setEmail( user.getEmail() );
        if ( user.getGender() != null ) {
            userDto.setGender( user.getGender().name() );
        }
        userDto.setPicture( user.getPicture() );
        userDto.setCountry( user.getCountry() );
        userDto.setState( user.getState() );
        userDto.setCity( user.getCity() );

        return userDto;
    }

    @Override
    public List<UserDto> toDtoList(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( user.size() );
        for ( User user1 : user ) {
            list.add( toDto( user1 ) );
        }

        return list;
    }

    @Override
    public User toDomain(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );
        user.setName( userDto.getName() );
        user.setEmail( userDto.getEmail() );
        if ( userDto.getGender() != null ) {
            user.setGender( Enum.valueOf( Gender.class, userDto.getGender() ) );
        }
        user.setPicture( userDto.getPicture() );
        user.setCountry( userDto.getCountry() );
        user.setState( userDto.getState() );
        user.setCity( userDto.getCity() );

        return user;
    }
}
