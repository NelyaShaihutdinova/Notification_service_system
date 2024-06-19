Feature: Аутентификация
  Background:
    * url "http://localhost:3001"
  Scenario: Аутентификация существующего пользователя с некорректным паролем
    * def authReq =
      """
        {"email": "fohado@mail.ru", "password": "qwe12345"}
      """
    Given path "/login"
    And request authReq
    When method post
    Then status 403
  Scenario: Аутентификация несуществующего пользователя
    * def authReq =
      """
        {"email": "fohado123@mail.ru", "password": "qwe12345"}
      """
    Given path "/login"
    And request authReq
    When method post
    Then status 401
  Scenario: Аутентификация пользователя с некорректно введённым паролем
    * def authReq =
      """
        {"email": "fohado@mail.ru", "password": "q"}
      """
    Given path "/login"
    And request authReq
    When method post
    Then status 400