package ru.ifmo.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.backend.models.Person;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
  Optional<Person> findByUsername(String username);

  Optional<Person> findByEmail(String email);

  Optional<Person> findByVkId(int vkId);

  Optional<Person> findByTelegramChatId(long telegramChatId);
}