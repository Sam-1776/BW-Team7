package Team7.dao;

import Team7.classi.Manutenzione;
import Team7.superclassi.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ManutenzioneDAO {
  private EntityManager em;

    public ManutenzioneDAO(EntityManager em){
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


    public Manutenzione getInzioeFineManutenzione(Mezzo mezzo){
            TypedQuery<Manutenzione> query = em.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo =:mezzo ",Manutenzione.class);
            query.setParameter("mezzo", mezzo);
            if (query != null) {
                return query.getSingleResult();
            }
        return null;
    }
}
