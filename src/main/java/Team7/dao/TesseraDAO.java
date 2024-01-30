package Team7.dao;

import Team7.classi.Tessera;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TesseraDAO {
    private EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void saveDb(Tessera x){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(x);
        transaction.commit();
        System.out.println("Tessera salvata con successo!");
    }

    public Tessera getById (long id){
        Tessera found = em.find(Tessera.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Tessera non trovata");
            return null;
        }
    }

    public void delete (long id){
        Tessera found = em.find(Tessera.class, id);

        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(found);
            transaction.commit();
        }else {
            System.out.println("Tessera non trovata");
        }
    }



}
