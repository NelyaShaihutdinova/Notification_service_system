package ru.ifmo.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.backend.models.Person;
import ru.ifmo.backend.repositories.PeopleRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
  private final PeopleRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
    this.peopleRepository = peopleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<Person> findByUsername(String username) {
    return peopleRepository.findByUsername(username);
  }

  public Optional<Person> findByEmail(String email) {
    return peopleRepository.findByEmail(email);
  }

  public Optional<Person> findByVkId(int vkId) {
    return peopleRepository.findByVkId(vkId);
  }

  public Optional<Person> findByTelegramChatId(long telegramChatId) {
    return peopleRepository.findByTelegramChatId(telegramChatId);
  }

  @Transactional
  public Person save(Person person) {
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");
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