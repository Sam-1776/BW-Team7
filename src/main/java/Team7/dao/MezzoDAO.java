package Team7.dao;

import Team7.classi.*;
import Team7.superclassi.Mezzo;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Mezzo getById (long id){
        Mezzo found = em.find(Mezzo.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Utente non trovato");
            return null;
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
        System.out.printf("la manutenzione del mezzo %d è iniziata\n", mezzo.getId());
    }
    public void fineManutenzione(LocalDate dataFine, Mezzo mezzo) {
        if (mezzo.getServizio() == Servizio.SERVIZIO) {
            System.out.printf("il mezzo %d è in servizio\n", mezzo.getId());
            return;
        }
        mezzo.setServizio(Servizio.SERVIZIO);
        //carico la named query dalla classe manutenzione
        Query query = em.unwrap(Session.class).getNamedQuery("Manutenzione_attuale");

        Manutenzione manutenzione = (Manutenzione) query.getSingleResult();
        if (manutenzione == null) {
            System.out.printf("Nessuna manutenzione in corso per il mezzo %d\n", mezzo.getId());
            return;
        }
        manutenzione.setDataFine(dataFine);

        em.getTransaction().begin();
        em.merge(mezzo);
        em.merge(manutenzione);
        em.getTransaction().commit();
        System.out.printf("la manutenzione del mezzo %d è finita\n", mezzo.getId());
    }

    public Long numeroPercorrenzaTappa(Mezzo mezzo, Tappa tappa) {
        // criteriabuilder costruisce i criteri di ricerca della query
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Fermata> fermataRoot = criteriaQuery.from(Fermata.class);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(builder.equal(fermataRoot.get("mezzo"), mezzo));
        predicateList.add(builder.equal(fermataRoot.get("tappa"), tappa));

        criteriaQuery
                .select(builder.count(fermataRoot))
                .where(builder.and(predicateList.toArray(new Predicate[0])));

        Long times = em.createQuery(criteriaQuery).getSingleResult();

        System.out.printf("Il mezzo %d è passato per %s %d volte", mezzo.getId(), tappa.getNome(), times);

        return times;
    }

    public void percorriTappa(Mezzo mezzo, Tappa tappa) {
        Fermata fermata = new Fermata();
        fermata.setMezzo(mezzo);
        fermata.setTappa(em.find(Tappa.class, tappa.getId()));
        fermata.setOrario(tappa.getArrivo());

        em.getTransaction().begin();
        em.merge(fermata);
        em.getTransaction().commit();

    }
}
