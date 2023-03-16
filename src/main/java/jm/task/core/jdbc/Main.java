package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        //Util.getConnection();
        //Util.getSessionFactory().

        UserService userServ = new UserServiceImpl();

        //Создание таблицы
        userServ.createUsersTable();
        //Добавление 4 User(ов)
        User[] users ={
                new User("Vasya", "Pupkin", (byte) 31),
                new User("Kolya","Rubkin",(byte) 24),
                new User("Tolya","Zubkin", (byte) 13),
                new User("Polya","Gubkin",(byte) 26)};

        for (User user : users) {
            userServ.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        //Получение всех User
        userServ.getAllUsers();

        //Очистка таблицы
        userServ.cleanUsersTable();

        //Удаление таблицы
        userServ.dropUsersTable();

        //userServ.removeUserById(1);

    }
}
