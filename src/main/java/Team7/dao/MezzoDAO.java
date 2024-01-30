package Team7.dao;

import Team7.superclassi.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MezzoDAO {
    private EntityManager em;

    public MezzoDAO(EntityManager em){
        this.em = em;
    }

    public void saveTransport(Mezzo mezzo){
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(mezzo);
            transaction.commit();
            System.out.println("Elemento Salvato con successo");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
