package base.application.util.auth;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsernameUtilTests {

    @Test
    public void validation() {
        String[] usernamesFalse = { "name12", "name-_", "123456" };
        Arrays.stream(usernamesFalse)
                .forEach(a -> assertTrue("check username test: " + a, UsernameUtil.isValid(a)));
        String[] usernamesTrue = { "name1!", "", "-_-" };
        Arrays.stream(usernamesTrue).forEach(a -> assertFalse("check username test: ", UsernameUtil.isValid(a)));
    }
}