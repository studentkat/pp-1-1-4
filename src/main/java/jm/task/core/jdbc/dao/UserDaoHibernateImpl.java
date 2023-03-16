package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        String query = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));\n";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            System.out.println("БД users создана!");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        String query = "DROP TABLE IF EXISTS `mydbtest`.`users`";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            System.out.println("БД users удалена!");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        //Util.getSessionFactory().openSession().save(user);

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            System.out.println("Челик " + name + " " + lastName + " добавлен");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            System.out.println("Челик с ID= " + id +" удален!");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;


        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            users = (List<User>) session.createQuery("From User").list();

            System.out.println("Список всех Users получен!");
            users.forEach(System.out::println);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE `mydbtest`.`users`";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            System.out.println("Удалены все строки в таблице users!");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
