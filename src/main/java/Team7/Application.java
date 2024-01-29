package Team7;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        System.out.println("Hello World!");
    }
}
