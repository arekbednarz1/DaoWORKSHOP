package pl.coderslab;

import com.google.protobuf.StringValue;
import pl.coderslab.UserDao;
import pl.coderslab.entity.User;

import java.util.Arrays;

public class MainDao {
    public static void main(String[] args) {


          UserDao userDao = new UserDao();
//        User user = new User();
//
//        user.setUserName("Adam");
//        user.setEmail("abcd@o2.pl");
//        user.setPassword("123244pass");
//        userDao.create(user);
//
//        User update = UserDao.read(1);
//        assert update != null;
//        update.setUserName("Edek");
//        update.setEmail("adefe@wew.pl");
//        update.setPassword("pass");
//        userDao.update(update);
//
//        User user1 = UserDao.read(1);
//
//
//        UserDao dao = new UserDao();
//        User user2 = new User();
//        user2.setUserName("Krzysztof");
//        user2.setEmail("Kot");
//        user2.setPassword("pass");
//        dao.create(user2);
//
//
//        User []showAll = dao.showAll();
//        for (User one : showAll){
//            System.out.println(String.valueOf(one));
//        }

        userDao.delete(10);
        userDao.delete(8);


    }

}
