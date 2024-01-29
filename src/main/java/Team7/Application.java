package Team7;

import Team7.classi.Tessera;
import Team7.classi.Utente;
import Team7.dao.TesseraDAO;
import Team7.dao.UtenteDAO;
import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");
    private static Faker faker = new Faker(Locale.ITALY);
    private static Random rdm = new Random();

    static Supplier<Utente> generatoreUser = () ->{
        return new Utente(faker.name().firstName(), faker.name().lastName(), rdm.nextInt(1950,2010));
    };

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);


//        generateUserDb(ud);
//        generateUserCard(td, ud);


    }

    public static void generateUserDb(UtenteDAO x){
        for (int i = 0; i < 100; i++) {
            x.saveDb(generatoreUser.get());
        }
    }

    public static void generateUserCard(TesseraDAO x, UtenteDAO y){
        for (int i = 0; i < 20; i++) {
            long n = rdm.nextLong(1,100);
            Utente u = y.getById(n);
            if (u != null){
                if ( y.getElement(n) != null){
                    System.out.println("Hai già una tessera");
                    i--;
                }else {
                    Tessera t = new Tessera(generateData(), u);
                    x.saveDb(t);
                    u.setNumero_tessera(t);
                    y.saveDb(u);
                }
            }else {
                i--;
            }
        }
    }

    public static LocalDate generateData (){

        int year = rdm.nextInt(2023,2024);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;

    }
}
