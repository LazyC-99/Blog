import com.lzc.bean.User;
import com.lzc.dao.UserMapper;
import com.lzc.service.Impl.UserServiceImpl;
import com.lzc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class test {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserServiceImpl userService;

    /**
     *注册测试
     * @throws ParseException
     */
    @Test
    public void regTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        User user = new User();user.setName("admin"); user.setPass("123");
        user.setId(userService.getTotalUser()+1);
        user.setRegTime(sdf.parse(format));
        user.setAccessPermission(1);
        user.setStatus(1);
        System.out.println(user);
        System.out.println(userMapper.insertUser(user)==1);
    }
    /**
     *更新测试
     * @throws ParseException
     */
    @Test
    public void updateregTest() throws ParseException {
        User user = new User();
        user.setName("admin"); user.setPass("123456");
        user.setId(1);
        user.setAccessPermission(1);
        user.setStatus(1);
        userMapper.update(user);
    }
}
