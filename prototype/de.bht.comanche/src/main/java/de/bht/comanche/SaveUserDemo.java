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
        //saveUserDemo.createAndStoreUser("Ralf", "Zakoni", "bertaisthuebsch", new Date());
        //saveUserDemo.printUsers();
        saveUserDemo.promptForInput();
        saveUserDemo.printUsers();
        if (args[0].equals("store")) {
           // saveUserDemo.createAndStoreUser("Ralf", "Zakoni", "bertaisthuebsch", new Date());
        }
        if (args[0].equals("get")) {
            //saveUserDemo.printUsers();
        }
        HibernateUtil.getSessionFactory().close();
    }

    private void promptForInput() {
        Scanner scanIn = new Scanner(System.in);
        String input = "empty";
        while (true) {
           User user = new User();
           log("E N T E R   N E W   U S E R   D A T A");
           line();
           System.out.print(pre + "First Name > ");
           input = scanIn.nextLine();
           if (input.equals("")) {
               break;
           }
           System.out.println();
           user.setFirstName(input);
           line();
           System.out.print(pre + "Last Name > ");
           input = scanIn.nextLine();
           if (input.equals("")) {
               break;
           }
           System.out.println();
           user.setLastName(input);
           line();
           System.out.print(pre + "Password > ");
           input = scanIn.nextLine();
           if (input.equals("")) {
               break;
           }
           System.out.println();
           user.setPassword(input);
           line();
           log("Saving user to DB: " + user.getFirstName() + " | " + user.getLastName() + " | " + user.getPassword() + " | ");
           createAndStoreUser(user);
        }
    }

    private void createAndStoreUser(String firstName, String lastName, String password, Date birthdate) {
        User sampleUser = new User();
        sampleUser.setFirstName(firstName);
        sampleUser.setLastName(lastName);
        sampleUser.setPassword(password);
        sampleUser.setBirthdate(birthdate);
        createAndStoreUser(sampleUser);
    }

    private void createAndStoreUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    private void printUsers() {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List result = session.createQuery("from User").list();
            session.getTransaction().commit();
            System.out.println();
            System.out.println("Retrieving U S E R S from Database (Rock and Roll)  >>");
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
