package Team7.dao;

import Team7.classi.Manutenzione;
import Team7.superclassi.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ManutenzioneDAO {
    EntityManager em;

    ManutenzioneDAO(EntityManager em){
        this.em = em;
    }

    public Manutenzione getById (long id){
        Manutenzione found = em.find(Manutenzione.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Manutenzione non trovato");
            return null;
        }
    }


    public List<Manutenzione> getInzioeFineManutenzione(Mezzo mezzo){
        TypedQuery<Manutenzione> query = em.createQuery("SELECT m.datainizio AND m.datafine FROM manutenzione m JOIN m.mezzo mezzo WHERE mezzo=:mezzo ",Manutenzione.class);
        query.setParameter("mezzo", mezzo);
        return query.getResultList();
    }
}
