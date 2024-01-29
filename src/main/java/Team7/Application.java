package Team7;

import Team7.classi.Autobus;
import Team7.classi.Servizio;
import Team7.classi.Tratta;
import Team7.dao.MezzoDAO;
import Team7.dao.TrattaDAO;
import Team7.superclassi.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        TrattaDAO trattaDao = new TrattaDAO(em);

        Tratta tratta1 = new Tratta("Piazza Cavour","Manzoni",1.32);
        Mezzo autobus1 = new Autobus(generateData(), Servizio.SERVIZIO,tratta1, LocalDateTime.now(),LocalDateTime.now(),100);

       trattaDao.saveTransport(tratta1);
       mezzoDAO.saveTransport(autobus1);

    }

    private static  Random rdm = new Random();
    public static LocalDate generateData (){

        int year = rdm.nextInt(2022,2023);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);
        return randomDate;
    }
}
