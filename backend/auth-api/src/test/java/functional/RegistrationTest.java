package functional;

import com.intuit.karate.junit5.Karate;

class RegistrationTest {
    @Karate.Test
    Karate testRegistration() {
        return Karate.run("C:\\Users\\fohad\\IdeaProjects\\Notification_service_system\\backend\\auth-api\\src\\test\\java\\functional\\Registration.feature");
    }
}