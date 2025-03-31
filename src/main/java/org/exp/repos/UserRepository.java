package org.exp.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.exp.entities.User;
import org.exp.config.DB;
import org.exp.usekeys.UseKeys;

import java.util.Optional;

public class UserRepository implements UseKeys {
    private static UserRepository instance;
    private final EntityManager entityManager;

    private UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository(DB.EMF.createEntityManager());
        }
        return instance;
    }

    @Override
    public void save(Object obj) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Object findById(Object obj) {
        return Optional.ofNullable(entityManager.find(User.class, obj));
    }

    public void updateLanguage(Long userId, String languageCode) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                user.setLanguage(languageCode);
                entityManager.merge(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePhoneNumber(Long userId, String phoneNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                user.setPhone(phoneNumber); // Telefon raqamini o‘rnatish
                entityManager.merge(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
