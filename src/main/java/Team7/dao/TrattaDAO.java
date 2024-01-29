package Team7.dao;

import Team7.classi.Tratta;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TrattaDAO {
    private EntityManager em;
    public TrattaDAO(EntityManager em){
        this.em = em;
    }

    public void saveSection(Tratta tratta){
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(tratta);
            transaction.commit();
            System.out.println("Elemento Salvato con successo");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
