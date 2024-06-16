package com.jrg.techchallenge.infrastructure.db.springdata.mapper;

import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.domain.User.Gender;
import com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-23T10:58:42+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
*/
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User toDomain(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userEntity.getUsername() );
        user.setName( userEntity.getName() );
        user.setEmail( userEntity.getEmail() );
        user.setGender( genderToGender( userEntity.getGender() ) );
        user.setPicture( userEntity.getPicture() );
        user.setCountry( userEntity.getCountry() );
        user.setState( userEntity.getState() );
        user.setCity( userEntity.getCity() );

        return user;
    }

    @Override
    public UserEntity toDbo(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( user.getUsername() );
        userEntity.setName( user.getName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setGender( genderToGender1( user.getGender() ) );
        userEntity.setPicture( user.getPicture() );
        userEntity.setCountry( user.getCountry() );
        userEntity.setState( user.getState() );
        userEntity.setCity( user.getCity() );

        return userEntity;
    }

    protected Gender genderToGender(com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity.Gender gender) {
        if ( gender == null ) {
            return null;
        }

        Gender gender1;

        switch ( gender ) {
            case MALE: gender1 = Gender.MALE;
            break;
            case FEMALE: gender1 = Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }

    protected com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity.Gender genderToGender1(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity.Gender gender1;

        switch ( gender ) {
            case MALE: gender1 = com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity.Gender.MALE;
            break;
            case FEMALE: gender1 = com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity.Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }
}
