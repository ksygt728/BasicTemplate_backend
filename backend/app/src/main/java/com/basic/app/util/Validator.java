package com.basic.app.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.basic.app.entity.baseEntity.BaseEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Validator {

  /*
   * 모두 존재하는 경우 (List형식의 update, delete에서 사용)
   */
  public static <S extends BaseEntity, K> Optional<List<S>> existsAll(JpaRepository<S, K> jpaRepository,
      Function<S, K> idExtractor, List<K> keys) {

    List<S> list = jpaRepository.findAllById(keys)
        .stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .toList();

    return (!list.isEmpty() && list.stream().map(idExtractor).allMatch(keys::contains))
        ? Optional.of(list)
        : Optional.empty();
  }

  /*
   * 하나라도 존재하는 경우 (List형식의 insert에서 사용)
   */
  public static <S extends BaseEntity, K> Optional<List<S>> existsOne(JpaRepository<S, K> jpaRepository,
      List<K> keys) {

    List<S> list = jpaRepository.findAllById(keys)
        .stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .toList();

    return list.isEmpty() ? Optional.empty() : Optional.of(list);

  }

}
