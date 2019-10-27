package base.application.util.auth;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordUtilTests {

    @Test
    public void checkErrorTest() {
        String[] passwords = { "Pass12", "Pass!@#$%^&", "pass\\-_!" };
        Arrays.stream(passwords).forEach(a -> assertFalse("check password test: " + a, PasswordUtil.checkError(a)));
    }
}