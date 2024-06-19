Feature: Регистрация
  Background:
    * url "notification-system.com"
  Scenario: Регистрация нового аккаунта
    * def createReq =
      """
        {"username": "nelyash", "email": "willy71@mail.ru", "password": "qwe123"}
      """
    Given path "/auth-api/registration"
    And request createReq
    When method post
    Then status 201
  Scenario: Регистрация существующего аккаунта
    * def createReq =
      """
        {"username": "nelya", "email": "fohado@mail.ru", "password": "qwe123"}
      """
    Given path "/auth-api/registration"
    And request createReq
    When method post
    Then status 400
  Scenario: Регистрация аккаунта с некорректным паролем
    * def createReq =
      """
        {"username": "nelya", "email": "fohado@mail.ru", "password": "q"}
      """
    Given path "/auth-api/registration"
    And request createReq
    When method post
    Then status 400
  Scenario: Регистрация аккаунта с некорректной почтой
    * def createReq =
      """
        {"username": "nelya", "email": "foh", "password": "qwe123"}
      """
    Given path "/auth-api/registration"
    And request createReq
    When method post
    Then status 400
  Scenario: Регистрация аккаунта с некорректным именем
    * def createReq =
      """
        {"username": "n", "email": "fohado@mail.ru", "password": "q"}
      """
    Given path "/auth-api/registration"
    And request createReq
    When method post
    Then status 400