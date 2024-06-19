Feature: UI
  Background:
    * configure driver = {type: 'chrome', executable: 'C:\Users\Public\Desktop\Google Chrome.lnk', addOptions: ["--remote-allow-origins=*"]}

  Scenario: UI Test
    Given driver "notification-system.com"
    And input('label[id=SignUpTab]', Key.ENTER)
    And input('input[id=username2]', Key.ENTER)
    And input('input[id=username2]', 'abc')
    And input('input[id=mail]', Key.ENTER)
    And input('input[id=mail]', 'nelyashaihutdinova@yandex.ru')
    And input('input[id=password2]', Key.ENTER)
    And input('input[id=password2]', 'asd123')
    And input('button[id=signUpBtn]', Key.ENTER)
    * java.lang.Thread.sleep(5000)