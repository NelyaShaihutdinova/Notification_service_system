Feature: Аутентификация
  Background:
    * url "notification-system.com"
  Scenario: Аутентификация существующего пользователя с корректным паролем
    * def authReq =
      """
        {"email": "fohado@mail.ru", "password": "qwe123"}
      """
    Given path "/auth-api/login"
    And request authReq
    When method post
    Then status 200
  Scenario: Аутентификация существующего пользователя с некорректным паролем
    * def authReq =
      """
        {"email": "fohado@mail.ru", "password": "qwe12345"}
      """
    Given path "/auth-api/login"
    And request authReq
    When method post
    Then status 401
  Scenario: Аутентификация несуществующего пользователя
    * def authReq =
      """
        {"email": "fohado123@mail.ru", "password": "qwe12345"}
      """
    Given path "/auth-api/login"
    And request authReq
    When method post
    Then status 401
  Scenario: Аутентификация пользователя с некорректно введённым паролем
    * def authReq =
      """
        {"email": "fohado@mail.ru", "password": "q"}
      """
    Given path "/auth-api/login"
    And request authReq
    When method post
    Then status 400