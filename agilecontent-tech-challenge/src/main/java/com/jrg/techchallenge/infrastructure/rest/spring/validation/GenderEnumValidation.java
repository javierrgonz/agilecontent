package com.jrg.techchallenge.infrastructure.rest.spring.validation;

import com.jrg.techchallenge.domain.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenderEnumValidation implements ConstraintValidator<GenderEnum, CharSequence> {
  private List<String> acceptedValues;

  @Override
  public void initialize(GenderEnum annotation) {
    acceptedValues = Stream.of(User.Gender.values())
      .map(Enum::name)
      .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return acceptedValues.contains(value.toString());
  }
}
