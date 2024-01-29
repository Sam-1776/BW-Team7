package Team7.dao;

import Team7.superclassi.Biglietto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
}
