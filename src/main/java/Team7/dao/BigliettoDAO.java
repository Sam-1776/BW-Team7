package Team7.dao;

import Team7.superclassi.Biglietto;
import Team7.superclassi.Emissione_Biglietti;
import Team7.superclassi.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BigliettoDAO {
    private EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void saveBiglietto(Biglietto biglietto) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(biglietto);
            transaction.commit();
            System.out.println("Elemento Salvato con successo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Biglietto getById(long id) {
        Biglietto found = em.find(Biglietto.class, id);
        if (found != null) {
            return found;
        } else {
            System.out.println("Biglietto non trovato");
            return null;
        }
    }

    public List<Biglietto> getBigliettiMezzo(Mezzo mezzo) {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.mezzo = :mezzo ", Biglietto.class);
        query.setParameter("mezzo", mezzo);
        return query.getResultList();
    }

    ;

    public void timbraBiglietto(Biglietto biglietto, Mezzo mezzo) {
        try {
            if (biglietto.getTimbro() == null) {
                LocalDateTime timbro = LocalDateTime.now();
                biglietto.setTimbro(timbro);
                biglietto.setMezzo(mezzo);

                em.getTransaction().begin();
                em.merge(biglietto);
                em.getTransaction().commit();
            } else {
                System.out.println("Biglietto già timbrato!");
            }
        } catch (Exception e) {
            System.out.println("Errore durante il timbro del biglietto: " + e.getMessage());

        }
    }


    public List<Biglietto> getBigliettiTimbratiInGiorno(LocalDate giorno) {
        LocalDateTime inizioGiorno = giorno.atStartOfDay();
        LocalDateTime fineGiorno = giorno.atTime(23, 59, 59);
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.timbro BETWEEN :inizioGiorno AND :fineGiorno", Biglietto.class);

        query.setParameter("inizioGiorno", inizioGiorno);
        query.setParameter("fineGiorno", fineGiorno);

        return query.getResultList();
    }

    public List<Biglietto> getBigliettiPerPuntoDiEmissione(Emissione_Biglietti emissione){
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.emissioneBiglietti=:emissioneBiglietti", Biglietto.class);
        query.setParameter("emissioneBiglietti", emissione);
        return query.getResultList();
    };






    public List<Biglietto> getItAndCheckExistence(long id) {
        TypedQuery<Biglietto> getIt = em.createQuery("SELECT b FROM Biglietto b WHERE b.tessera.id =:id", Biglietto.class);
        getIt.setParameter("id", id);
        if (getIt != null) {
            return getIt.getResultList();
        }
        return null;
    }
}
