package jay.io.primefacesdemo.users.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jay.io.primefacesdemo.users.model.User;


import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public User find(int id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }


    public List<User> searchByUsername(String keyword) {
        return em.createQuery("SELECT u FROM User u WHERE u.username LIKE :kw", User.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }

}
