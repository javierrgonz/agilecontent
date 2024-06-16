package com.jrg.techchallenge.infrastructure.rest.randomuser.mapper;

import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.randomuser.dto.ParserUser;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParserUserMapper {

  default Map<String, User> toUserMap(List<ParserUser> parserUserList) {
    return parserUserList.stream().map(parsedUser -> new User(
      parsedUser.getLogin().getUsername(),
      parsedUser.getName().getCompleteName(),
      parsedUser.getEmail(),
      User.Gender.valueOf(parsedUser.getGender().toUpperCase()),
      parsedUser.getPicture().getMedium(),
      parsedUser.getLocation().getCountry(),
      parsedUser.getLocation().getState(),
      parsedUser.getLocation().getCity())
    ).collect(Collectors.toMap(
      user -> user.getUsername(),
      user -> user
    ));
  }


}
