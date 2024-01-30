package Team7.dao;

import Team7.classi.Manutenzione;
import Team7.classi.Servizio;
import Team7.superclassi.Mezzo;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDate;

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

    public void iniziaManutenzione(LocalDate dataInizio, Mezzo mezzo) {
        if (mezzo.getServizio() == Servizio.MANUTENZIONE) {
            System.out.printf("Il mezzo %d è già in manutenzione", mezzo.getId());
            return;
        }
        mezzo.setServizio(Servizio.MANUTENZIONE);

        Manutenzione manutenzione = new Manutenzione();
        manutenzione.setDataInizio(dataInizio);
        manutenzione.setMezzo(mezzo);

        em.getTransaction().begin();
        em.merge(manutenzione);
        em.merge(mezzo);
        em.getTransaction().commit();
        System.out.printf("la manutenzione del mezzo %d è iniziata", mezzo.getId());
    }
    public void fineManutenzione(LocalDate dataFine, Mezzo mezzo) {
        if (mezzo.getServizio() == Servizio.SERVIZIO) {
            System.out.printf("il mezzo %d è in servizio", mezzo.getId());
            return;
        }
        mezzo.setServizio(Servizio.SERVIZIO);

        Query query = em.unwrap(Session.class).getNamedQuery("Manutenzione_attuale");

        Manutenzione manutenzione = (Manutenzione) query.getSingleResult();
        if (manutenzione == null) {
            System.out.printf("Nessuna manutenzione in corso per il mezzo %d", mezzo.getId());
            return;
        }
        manutenzione.setDataFine(dataFine);

        em.getTransaction().begin();
        em.merge(mezzo);
        em.merge(manutenzione);
        em.getTransaction().commit();
    }

}
