package Team7.dao;

import Team7.classi.Tappa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TappaDAO {
    private EntityManager em ;

    public TappaDAO(EntityManager em){
        this.em = em;
    }

    public void saveTappa(Tappa tappa){
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(tappa);
            transaction.commit();
            System.out.println("Tappa Salvato con successo");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Tappa getById (long id){
        Tappa found = em.find(Tappa.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Tappa non trovato");
            return null;
        }
    }


}
