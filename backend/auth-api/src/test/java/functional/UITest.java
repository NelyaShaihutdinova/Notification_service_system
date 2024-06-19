package functional;

import com.intuit.karate.junit5.Karate;

class UITest {
    @Karate.Test
    Karate testRegistration() {
        return Karate.run("UITest").relativeTo(getClass());
    }
}