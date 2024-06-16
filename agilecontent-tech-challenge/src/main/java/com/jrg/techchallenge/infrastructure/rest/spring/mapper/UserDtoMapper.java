package com.jrg.techchallenge.infrastructure.rest.spring.mapper;

import org.mapstruct.Mapper;

import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.spring.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

  UserDto toDto(User user);

  List<UserDto> toDtoList(List<User> user);

  default Map<String, Map<String, Map<String, List<UserDto>>>> toUserDtoMap(Map<String, Map<String, Map<String, List<User>>>> userMap) {
    if (userMap == null) {
      return null;
    }
    return userMap.entrySet().stream()
      .collect(Collectors.toMap(
        entry1 -> entry1.getKey(),
        entry1 -> entry1.getValue().entrySet().stream()
          .collect(Collectors.toMap(
            entry2 -> entry2.getKey(),
            entry2 -> entry2.getValue().entrySet().stream()
              .collect(Collectors.toMap(
                entry3 -> entry3.getKey(),
                entry3 -> entry3.getValue().stream()
                  .map(user -> toDto(user))
                  .collect(Collectors.toList())
              ))
          ))
      ));
  }

  User toDomain(UserDto userDto);
}
