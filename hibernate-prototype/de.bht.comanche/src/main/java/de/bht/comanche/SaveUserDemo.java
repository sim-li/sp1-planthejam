package de.bht.comanche;

import org.hibernate.Session;
import java.util.*;
import java.lang.*;
import de.bht.comanche.HibernateUtil;
import de.bht.comanche.User;

public class SaveUserDemo {
    public final String pre = "*|";

    public static void main(String[] args) {
        SaveUserDemo saveUserDemo = new SaveUserDemo();
        saveUserDemo.createAndStoreUser("Ralf", "Zakoni", "bertaisthuebsch", new Date());
        saveUserDemo.printUsers();
        if (args[0].equals("store")) {
           // saveUserDemo.createAndStoreUser("Ralf", "Zakoni", "bertaisthuebsch", new Date());
        }
        if (args[0].equals("get")) {
            //saveUserDemo.printUsers();
        }
        HibernateUtil.getSessionFactory().close();
    }
 /*
    private void promptForInput() {
        Scanner scanner = new Scanner(System.in);
        String input = "empty";
        while (true) {
           log("E N T E R   N E W   U S E R   D A T A");
           line();
           System.print(pre + "First Name > ");
           input = scanIn.nextLine();
           line();
           System.print(pre + "Last Name > ");
           input = scanIn.nextLine();
           line();
           System.print(pre + "Password > ");
           input = scanIn.nextLine();
           line();
        }
    }
*/
    private void createAndStoreUser(String firstName, String lastName, String password, Date birthdate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User sampleUser = new User();
        sampleUser.setFirstName(firstName);
        sampleUser.setLastName(lastName);
        sampleUser.setPassword(password);
        sampleUser.setBirthdate(birthdate);
        session.save(sampleUser);
        session.getTransaction().commit();
        /*
        Session session2 = HibernateUtil.getSessionFactory().getCurrentSession();
        session2.beginTransaction();
        User storedUser = (User) session2.load(User.class, 1L);
        session2.getTransaction().commit();

        */
    }

    private void printUsers() {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List result = session.createQuery("from User").list();
            session.getTransaction().commit();
            System.out.println();
            System.out.println("Retrieving U S E R S from Database (Starting to Rol)  >>");
            for (Object obj: result) {
                User user = (User) obj;
                space();
                log("U S E R | ID : " + user.getId());
                line();
                log("First Name: " + user.getFirstName());
                log("Last Name: " + user.getLastName());
                log("Password: " + user.getPassword());
                log("Birthdate: " +  user.getBirthdate());
                line();
            }
    }

    private void log(String msg) {
        System.out.println(pre + msg);
    }

    private void line() {
        System.out.println(pre + "=======================================================================");
    }

    private void space() {
        System.out.println(pre);
    }
}
