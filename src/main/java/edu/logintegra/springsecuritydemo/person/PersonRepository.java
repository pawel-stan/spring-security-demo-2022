package edu.logintegra.springsecuritydemo.person;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByUsername(String username);

    Optional<Person> findByUsernameAndEnabled(String username, Boolean enabled);

    Iterable<Person> findAllByUsernameOrPassword(String s1, String s2);

    Iterable<Person> findAllByDateCreatedAfter(Date d);
}
