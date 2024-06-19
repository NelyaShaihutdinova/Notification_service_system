package functional;

import com.intuit.karate.junit5.Karate;

class AuthTest {
    @Karate.Test
    Karate testAuth() {
        return Karate.run("C:\\Users\\fohad\\IdeaProjects\\Notification_service_system\\backend\\auth-api\\src\\test\\java\\functional\\Auth.feature");
    }
}