package functional;

import com.intuit.karate.junit5.Karate;

class UITest {
    @Karate.Test
    Karate testRegistration() {
        return Karate.run("C:\\Users\\fohad\\IdeaProjects\\Notification_service_system\\backend\\auth-api\\src\\test\\java\\functional\\UITest.feature");
    }
}