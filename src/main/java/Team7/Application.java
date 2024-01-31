package Team7;

import Team7.classi.*;
import Team7.dao.*;
import Team7.superclassi.Biglietto;
import Team7.superclassi.Emissione_Biglietti;
import Team7.superclassi.Mezzo;
import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;


public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");
    private static Faker faker = new Faker(Locale.ITALY);
    private static Random rdm = new Random();

    static Supplier<Utente> generatoreUser = () -> {
        return new Utente(faker.name().firstName(), faker.name().lastName(), rdm.nextInt(1950, 2010));
    };

    static Supplier<Emissione_Biglietti> generateDispenser = () ->{
        if (rdm.nextLong(2) == 1){
            return new Distributore(Stato.ATTIVO);
        }else {
            return new Distributore(Stato.FUORI_SERVIZIO);
        }
    };

    static  Supplier<Emissione_Biglietti> generateDealer = () ->{
        return new Rivenditore(faker.company().name(), faker.address().country());
    };

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        TrattaDAO trattaDao = new TrattaDAO(em);
        EmissioneDAO emissioneDAO = new EmissioneDAO(em);
        BigliettoDAO bigliettoDAO = new BigliettoDAO(em);
        TappaDAO tappaDAO = new TappaDAO(em);


        Tratta tratta1 = new Tratta("Piazza Cavour", "Manzoni", 1.32);
        Tappa tappa1 = new Tappa("Piazza Euclide", tratta1);
        Mezzo autobus1 = new Autobus(generateData(), Servizio.SERVIZIO, tratta1, LocalDateTime.now(), LocalDateTime.now(), 100);
        Emissione_Biglietti d1 = new Distributore(Stato.ATTIVO);
        Emissione_Biglietti r1 = new Rivenditore(faker.company().name(), faker.address().country());
        Tratta tratta2 = new Tratta("Piazza Garibaldi", "Toledo", 0.00);
        tratta2.setTempoMedio(tratta2.calcoloTempoPrevisto().toSeconds());

        emissioneDAO.saveDb(d1);

        Biglietto biglietto1 = new Biglietto(LocalDate.now(), d1);
       Tessera t = td.getById(101);
       Biglietto a1 = new Abbonamento(LocalDate.now(), d1, Periodicita.SETTIMANALE, t);
       Biglietto provaBi= new Biglietto(LocalDate.now(),d1);
       bigliettoDAO.saveBiglietto(provaBi);
       bigliettoDAO.saveBiglietto(biglietto1);
       bigliettoDAO.saveBiglietto(a1);

       tappaDAO.saveTappa(tappa1);
        trattaDao.saveSection(tratta1);
         mezzoDAO.saveTransport(autobus1);


        bigliettoDAO.timbraBiglietto(biglietto1,autobus1);

        bigliettoDAO.timbraBiglietto(provaBi,autobus1);


        bigliettoDAO.timbraBiglietto(biglietto1,autobus1);


        System.out.println("Numeri biglietti timbrati sulla tratta "+ autobus1.getTratta() + "sono: " + bigliettoDAO.getBigliettiMezzo(autobus1).size());
        System.out.println("Numeri biglietti timbrati il giorno sono: " +bigliettoDAO.getBigliettiTimbratiInGiorno(LocalDate.now()).size());




       bigliettoDAO.saveBiglietto(biglietto1);
       bigliettoDAO.saveBiglietto(a1);

        tappaDAO.saveTappa(tappa1);
        trattaDao.saveSection(tratta1);
       mezzoDAO.saveTransport(autobus1);



       generateUserDb(ud);
        generateUserCard(td, ud);
        generateEmitter(emissioneDAO);
       createTicket(bigliettoDAO, emissioneDAO);
        Utente utente = ud.getById(1);
        Tessera tessera = new Tessera(LocalDate.now().minusYears(1), utente);
         td.saveDb(tessera);
        createTicketRivenditore(bigliettoDAO,emissioneDAO,td);

        System.out.println(bigliettoDAO.getBigliettiPerPuntoDiEmissione(d1)); ;


    }

    public static void generateUserDb(UtenteDAO x) {
        for (int i = 0; i < 100; i++) {
            x.saveDb(generatoreUser.get());
        }
    }

    public static void generateUserCard(TesseraDAO x, UtenteDAO y) {
        for (int i = 0; i < 20; i++) {
            long n = rdm.nextLong(1, 100);
            Utente u = y.getById(n);
            if (u != null) {
                if (y.getElement(n) != null) {
                    System.out.println("Hai già una tessera");
                    i--;
                } else {
                    Tessera t = new Tessera(generateData(), u);
                    x.saveDb(t);
                    u.setNumero_tessera(t);
                    y.saveDb(u);
                }
            } else {
                i--;
            }
        }
    }

    public static LocalDate generateData() {

        int year = rdm.nextInt(2022, 2023);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;
    }




    public static void generateEmitter(EmissioneDAO x){
        for (int i = 0; i < 50; i++) {
        long y = rdm.nextLong(1, 3);
        System.out.println(y);
            if (y == 1){
                x.saveDb(generateDispenser.get());
            }else {
                x.saveDb(generateDealer.get());
            }
        }
    }

    public static void createTicket(BigliettoDAO x, EmissioneDAO y){
        System.out.println("Inserire numero Distributore");
        Scanner scanner = new Scanner(System.in);
        long input = scanner.nextLong();
        Emissione_Biglietti e = y.getAndCheck(input);
        if (e != null){
            x.saveBiglietto(new Biglietto(LocalDate.now(), e));
            scanner.close();
        }else {
            System.out.println("Il distributore è fuori servizio");
            scanner.close();
        }
    }

    public static void createTicketRivenditore(BigliettoDAO x, EmissioneDAO y, TesseraDAO z){
        Scanner scanner = new Scanner(System.in);
        String str = "";
        Scanner number = new Scanner(System.in);
        System.out.println("Inserire numero Rivenditore");
        long input = number.nextLong();
        Emissione_Biglietti e = y.getAndCheckType(input);
        if (e != null){
            System.out.println("Hai una tessera?");
            str = scanner.nextLine();
            if (str.equals("si")){
                System.out.println("Inserire numero tessera");
                input = number.nextLong();
                Tessera t = z.getById(input);
                if (t != null){
                    if (checkExpiration(t) == true){
                        System.out.println("Tessera scaduta" + "\n" + "vuoi rinnovarlo?");
                        str = scanner.nextLine();
                        if (str.equals("si")){
                            t.setData(LocalDate.now());
                            t.setData_scadenza(LocalDate.now().plusYears(1));
                            z.saveDb(t);
                            makeSeasonTicket(x,e,t);
                        }else{
                            x.saveBiglietto(new Biglietto(LocalDate.now(), e));
                        }
                    }else {
                        List<Biglietto> bList = x.getItAndCheckExistence(input);
                        if (bList.isEmpty()){
                            makeSeasonTicket(x,e,t);
                        }else {
                            System.out.println("Hai già un abbonamento");
                        }
                    }
                }
            }else {
                System.out.println("vuoi tesserarti?");
                str = scanner.nextLine();
                if (str.equals("si")){
                    System.out.println("WIP");
                }else {
                    x.saveBiglietto(new Biglietto(LocalDate.now(), e));
                }
            }
        }else {
            System.out.println("Rivenditore non trovato");
        }

    }

    public static boolean checkExpiration(Tessera y){
        int yearT = y.getData_scadenza().getYear();
        int monthT = y.getData_scadenza().getMonthValue();
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        if (yearT == year && monthT >= month){
            return true;
        }
        return false;
    }

    public static void makeSeasonTicket(BigliettoDAO x, Emissione_Biglietti y, Tessera z){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Che periodo vuoi l'abbonamento");
        String str = scanner.nextLine();
        if (str.equals(Periodicita.SETTIMANALE.toString().toLowerCase())){
            x.saveBiglietto(new Abbonamento(LocalDate.now(), y, Periodicita.SETTIMANALE, z));
        }else {
            x.saveBiglietto(new Abbonamento(LocalDate.now(), y, Periodicita.MENSILE, z));
        }
    }

    
}
