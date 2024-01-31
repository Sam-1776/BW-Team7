package Team7.dao;

import Team7.classi.Distributore;
import Team7.classi.Rivenditore;
import Team7.classi.Stato;
import Team7.superclassi.Emissione_Biglietti;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EmissioneDAO {

    private EntityManager em;

    public EmissioneDAO(EntityManager em) {
        this.em = em;
    }

    public void saveDb(Emissione_Biglietti x){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(x);
        transaction.commit();
        System.out.println("Luogo di distribuzione salvato con successo!");
    }

    public Emissione_Biglietti getById (long id){
        Emissione_Biglietti found = em.find(Emissione_Biglietti.class, id);
        if (found != null) {
            return found;
        }else {
            System.out.println("Luogo di distribuzione non trovato");
            return null;
        }
    }

    public Emissione_Biglietti getAndCheck(long id){
        Emissione_Biglietti found = em.find(Emissione_Biglietti.class, id);
        if (found instanceof Distributore){
            if (((Distributore) found).getStato().equals(Stato.ATTIVO)){
                return found;
            }
        }
        return null;
    }

    public Emissione_Biglietti getAndCheckType(long id){
        Emissione_Biglietti found = em.find(Emissione_Biglietti.class, id);
        if (found instanceof Rivenditore){
            return found;
        }
        return null;
    }
}
