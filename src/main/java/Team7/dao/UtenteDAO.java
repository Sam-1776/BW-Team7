package Team7.dao;

import Team7.classi.Utente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void saveDb(Utente x){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(x);
        transaction.commit();
        System.out.println("Utente salvato con successo!");
    }

    public Utente getById (long id){
        Utente found = em.find(Utente.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Utente non trovato");
            return null;
        }
    }

    public void delete (long id){
        Utente found = em.find(Utente.class, id);

        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(found);
            transaction.commit();
        }else {
            System.out.println("Utente non trovato");
        }
    }

    public Utente getElement(long id){
        Utente found = em.find(Utente.class, id);
        if (found.getNumero_tessera() != null) {
           return found;
        }
        return null;
    }
}
