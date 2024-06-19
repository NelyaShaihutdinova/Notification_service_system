package functional;

import com.intuit.karate.junit5.Karate;

class RegistrationTest {
    @Karate.Test
    Karate testRegistration() {
        return Karate.run("Registration").relativeTo(getClass());
    }
}