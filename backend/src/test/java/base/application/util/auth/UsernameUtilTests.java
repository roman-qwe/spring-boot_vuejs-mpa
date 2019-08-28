package base.application.util.auth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsernameUtilTests {

    @Test
    public void checkErrorTest() {
        String[] usernamesFalse = { "name12", "name-_", "123456" };
        Arrays.stream(usernamesFalse)
                .forEach(a -> assertFalse("check username test: " + a, UsernameUtil.checkError(a)));
        String[] usernamesTrue = { "name1!", "", "-_", "-_-_-" };
        Arrays.stream(usernamesTrue).forEach(a -> assertTrue("check username test: ", UsernameUtil.checkError(a)));
    }
}