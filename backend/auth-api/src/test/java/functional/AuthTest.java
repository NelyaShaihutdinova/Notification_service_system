package functional;

import com.intuit.karate.junit5.Karate;

class AuthTest {
    @Karate.Test
    Karate testAuth() {
        return Karate.run("Auth").relativeTo(getClass());
    }
}