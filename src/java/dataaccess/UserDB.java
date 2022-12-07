package dataaccess;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;
import services.UserService;

public class UserDB {

    public ArrayList<User> getAllUsers() throws Exception {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            // Find all of the users in the database
            List<User> usersListType = em.createNamedQuery("User.findAll", User.class).getResultList();
            ArrayList<User> users = new ArrayList(usersListType);
            // Return list of users
            return users;

        } finally {
            em.close();
        }
    }

    public User findUser(String email) throws Exception {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            // Find the user based on their email
            User user = em.find(User.class, email);
            // Return the user
            return user;

        } finally {
            em.close();
        }
    }

    public void insertNewUser(User user) throws Exception {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // Insert the user into the user table
            em.persist(user);

            // Roles have multiple users so this new user should be added to the respective role list
            Role role = user.getRole();
            role.getUserList().add(user);
            // update the role table
            em.merge(role);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

    public void updateUser(User user) throws Exception {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // update the user in the user table
            em.merge(user);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

    public void deleteUser(String email) throws Exception {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Find the user based on their email
            User user = em.find(User.class, email);

            // Transaction
            trans.begin();

            // Remove the user into the user table
            em.remove(em.merge(user));

            // Roles have multiple users so this new user should be remove from respective role list
            Role role = user.getRole();
            role.getUserList().remove(user);
            // update the role table
            em.merge(role);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

}
