package ru.ifmo.backend.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.backend.user.Person;
import ru.ifmo.backend.user.PersonRepository;
import ru.ifmo.backend.user.PersonRole;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
  private final PersonRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PeopleService(PersonRepository peopleRepository, PasswordEncoder passwordEncoder) {
    this.peopleRepository = peopleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<Person> findByUsername(String username) {
    return peopleRepository.findByUsername(username);
  }

  public Optional<Person> findByEmail(String email) {
    return peopleRepository.findByEmail(email);
  }

  @Transactional
  public Person save(Person person) {
    person.setRole(PersonRole.ROLE_USER);
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    return peopleRepository.save(person);
  }

  @Transactional
  public Person update(Person person) {
    return peopleRepository.save(person);
  }

  @Transactional
  public void delete(Person person) {
    peopleRepository.delete(person);
  }
}