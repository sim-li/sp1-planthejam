package de.bht.comanche;

import org.hibernate.Session;
import java.util.*;
import de.bht.comanche.HibernateUtil;
import de.bht.comanche.User;

public class SaveUserDemo {

    public static void main(String[] args) {
        SaveUserDemo saveUserDemo = new SaveUserDemo();
        if (args[0].equals("store")) {
            saveUserDemo.createAndStoreUser("Ralf", "Zakoni", "bertaisthuebsch", new Date());
        }
        HibernateUtil.getSessionFactory().close();
    }

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
    }

}
