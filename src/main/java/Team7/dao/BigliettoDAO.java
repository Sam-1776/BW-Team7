package Team7.dao;

import Team7.superclassi.Biglietto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class BigliettoDAO {
        private EntityManager em;
        public BigliettoDAO(EntityManager em){
            this.em = em;
        }

    public void saveBiglietto(Biglietto biglietto){
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(biglietto);
            transaction.commit();
            System.out.println("Elemento Salvato con successo");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Biglietto getById (long id){
        Biglietto found = em.find(Biglietto.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Biglietto non trovato");
            return null;
        }
    }

    public List<Biglietto> getItAndCheckExistence(long id){
        TypedQuery<Biglietto> getIt = em.createQuery("SELECT b FROM Biglietto b WHERE b.tessera.id =:id", Biglietto.class);
        getIt.setParameter("id", id);
        if (getIt != null){
            return getIt.getResultList();
        }
        return null;
    }
}
