package Team7.dao;

import javax.persistence.EntityManager;

public class BigliettoDAO {
        private EntityManager em;
        BigliettoDAO(EntityManager em){
            this.em = em;
        }
}
