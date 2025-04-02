package org.exp.repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.exp.config.DB;
import org.exp.entities.ForwardedMessage;
import org.exp.usekeys.RepoKeys;

import java.util.List;

public class ForwardedMessageRepository implements RepoKeys {
    private static ForwardedMessageRepository instance;
    private final EntityManager entityManager;

    private ForwardedMessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static synchronized ForwardedMessageRepository getInstance() {
        if (instance == null) {
            instance = new ForwardedMessageRepository(DB.EMF.createEntityManager());
        }
        return instance;
    }

    @Override
    public void save(Object obj1, Object obj2) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ForwardedMessage forwardedMessage = new ForwardedMessage((Integer) obj1, (Long) obj2);
            entityManager.persist(forwardedMessage);
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
        try {
            List<ForwardedMessage> result = entityManager.createQuery(
                            "SELECT fm FROM ForwardedMessage fm WHERE fm.messageId = :messageId", ForwardedMessage.class)
                    .setParameter("messageId", (Integer) obj)
                    .getResultList();

            return result.isEmpty() ? null : result.getFirst().getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
