package Team7.dao;

import javax.persistence.EntityManager;

public class TappaDAO {
    private EntityManager em ;

    TappaDAO(EntityManager em){
        this.em = em;
    }

}
