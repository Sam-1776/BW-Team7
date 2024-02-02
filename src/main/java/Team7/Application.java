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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;


public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");
    private static final Faker faker = new Faker(Locale.ITALY);
    private static final Random rdm = new Random();

    static Supplier<Utente> generatoreUser = () -> new Utente(faker.name().firstName(), faker.name().lastName(), rdm.nextInt(1950, 2010));

    static Supplier<Emissione_Biglietti> generateDispenser = () ->{
        if (rdm.nextLong(2) == 1){
            return new Distributore(Stato.ATTIVO);
        }else {
            return new Distributore(Stato.FUORI_SERVIZIO);
        }
    };



    static  Supplier<Tratta> generateTratta = () -> new Tratta(faker.address().streetName(),faker.address().streetName(), rdm.nextDouble(1.0,2.5));

    static  Supplier<Emissione_Biglietti> generateDealer = () -> new Rivenditore(faker.company().name(), faker.address().country());

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        UtenteDAO utenteDAO = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        TrattaDAO trattaDao = new TrattaDAO(em);
        EmissioneDAO emissioneDAO = new EmissioneDAO(em);
        BigliettoDAO bigliettoDAO = new BigliettoDAO(em);
        ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
        TappaDAO tappaDAO = new TappaDAO(em);

        Scanner scanner = new Scanner(System.in);


        /*
        Possiamo dividere in piccole funzioni contenenti switch
        Lo switch principale può essere posto nel main dove ricade una scelta per una porzione dle progetto
        ESEMPIO:
        caso 1 = creazione db
        caso 2 = tutte le funzioni inerenti i biglietti (Prima parte del progetto)
        caso 3 = tutte le funzioni del mezzo (Seconda parte del progetto)
        caso 4 = tutte le funzioni delle tratte (Terza parte del progetto)

        Tutti i casi hanno uno switch per poter scegliere poi la funzione da testare
        Per poter poi pulire ulteriormente il main possiamo snellire le funzioni lunghe e creare
        una classe che contenga tutti i metodi da utilizzare e lasciare nel main poi solo le funzioni con gli switch
         */

        //CREAZIONE ELEMENTI RICHIESTI GENERATI AUTOMATICAMENTE!!!

        //Creazione Utenti:
       // generateUserDb(utenteDAO);
        //Creazione Tessere:
       // generateUserCard(tesseraDAO,utenteDAO);

        //Creazione Distributore e Rivenditore:
       // generateEmitter(emissioneDAO);

        //Creazione Biglietti
        //createTicketRivenditore(bigliettoDAO,emissioneDAO,tesseraDAO,utenteDAO);

        //Creazione Tratte:
        // generateTrattaDb(trattaDao);

        //Creazione Mezzi:
       // generateMezzoTratta(mezzoDAO,trattaDao);

        //Controllo numeri biglietti erogati in un giorno specifico
        //System.out.println(bigliettoDAO.getBigliettiTotali(LocalDate.now()));


        //SERVIZI DELL'APPLICAZIONE

        System.out.println("Seleziona: \n" + "1- Generazione DB \n" + "2- Operazioni Biglietti \n" + "3- Operazione sui Mezzi \n" + "4- Operazioni sulle Tratte \n" + "0-ESCI");
        int scelta = scanner.nextInt();
        boolean ciclo = true;

            switch (scelta){
                case 1:
                    // genera automaticamente utenti casuali
                    generateUserDb(utenteDAO);
                    // genera automaticamente card per degli utenti presi casualmente
                    generateUserCard(tesseraDAO,utenteDAO);
                    // genera automaticamente distributori e punti vendita per i biglietti
                    generateEmitter(emissioneDAO);
                    // genera automaticamente tratte per i mezzi
                    generateTrattaDb(trattaDao);
                    //genera automaticamente tappe delle tratte
                    generateTappaTratta(trattaDao);
                    // genera automaticamente dei mezzi
                    generateMezzoTratta(mezzoDAO,trattaDao);
                    break;
                case 2:
                    // permette di testare tutte le funzioni dei biglietti abbonamenti e tessere
                    functionOnTicket(utenteDAO, bigliettoDAO, tesseraDAO, emissioneDAO);
                    break;
                case 3:
                    // permette di testare tutte le funzioni dei mezzi
                    functionOnTransport(mezzoDAO, trattaDao, bigliettoDAO, manutenzioneDAO);
                    break;
            }



    }

    public static void functionOnTicket(UtenteDAO x, BigliettoDAO y, TesseraDAO z, EmissioneDAO e){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleziona: \n" + "1- per andare dal rivenditore \n" +"2- per andare dal distributore \n" + "3- per il controllo dei biglietti erogati \n" + "4- controllo validità abbonamento \n" + "0 per uscire");
        int scelta = scanner.nextInt();
        switch (scelta){
            case 1:
                createTicketRivenditore(y,e,z,x);
                scanner.close();
                break;
            case 2:
                createTicket(y,e);
                scanner.close();
                break;
            case 3:
                //Controllo biglietti emessi da un distrubutore o da un rivenditore:
                System.out.println("Inserisci L' ID del distributore o del rivenditore: ");
                int scanner2= scanner.nextInt();
                Emissione_Biglietti emissione1 = e.getById(scanner2);
                System.out.println(y.getBigliettiPerPuntoDiEmissione(emissione1).size());
                scanner.close();
                break;
            case 4:
                validationSeasonTicket(x, z, y);
            default:
                System.out.println("Arrivederci");
                scanner.close();
                break;
        }
    }

    public static void functionOnTransport(MezzoDAO x, TrattaDAO y, BigliettoDAO z, ManutenzioneDAO m){
        Scanner scanner = new Scanner(System.in);
        long id = 0;
        System.out.println("Seleziona: \n" + "1- Metti in manutenzione un Mezzo \n" + "2- Metti in servizio un Mezzo \n" + "3- Controlla il periodo di manutenzione e servizio di un Mezzo \n" + "4- Timbro biglietto \n" + "5- Controllo biglietti timbrati su un mezzo \n" + "6- Controllo biglietti di un dato giorno");
        int scelta = scanner.nextInt();
        switch (scelta){
            case 1:
                System.out.println("Inserire id mezzo");
                id = scanner.nextLong();
                x.iniziaManutenzione(LocalDate.now(),x.getById(id));
                break;
            case 2:
                System.out.println("Inserire id mezzo");
                id = scanner.nextLong();
                x.fineManutenzione(LocalDate.now(),x.getById(id));
                break;
            case 3:
                System.out.println("Inserire id mezzo");
                id = scanner.nextLong();
                Manutenzione c = m.getInzioeFineManutenzione(x.getById(id));
                long daysBetween = ChronoUnit.DAYS.between(c.getDataFine(), c.getDataInizio());
                System.out.println(daysBetween);
                break;
            case 4:
                System.out.println("Inserire id mezzo");
                id = scanner.nextLong();
                System.out.println("Inserire id biglietto");
                long idb = scanner.nextLong();
                z.timbraBiglietto(z.getById(idb), x.getById(id));
                break;
            case 5:
                System.out.println("Inserire id mezzo");
                id = scanner.nextLong();
                z.getBigliettiMezzo(x.getById(id)).forEach(System.out::println);
                break;
            case 6:
                Scanner input = new Scanner(System.in);
                System.out.println("Inserire data da controllare");
                String str = input.nextLine();
                LocalDate day = LocalDate.parse(str);
                z.getBigliettiTimbratiInGiorno(day).forEach(System.out::println);
                break;
            default:
                System.out.println("Arrivederci");
                scanner.close();
                break;
        }
    }

    public static void generateUserDb(UtenteDAO x) {
        for (int i = 0; i < 100; i++) {
            x.saveDb(generatoreUser.get());
        }
    }
    public static void generateTrattaDb(TrattaDAO x) {
        for (int i = 0; i < 5; i++) {
            x.saveSection(generateTratta.get());
        }
    }

    public static void generateTappaTratta(TrattaDAO x){
        for (int i = 0; i < 50; i++) {
            long n = rdm.nextLong(171, 176);
            Tratta t = x.getById(n);
            if (t != null) {
                LocalDateTime arrivo = LocalDateTime.of(LocalDate.now(), LocalTime.of(rdm.nextInt(5,23), 00));
                t.getTappe().add(new Tappa(faker.address().streetAddress(), t, arrivo, arrivo.plusMinutes(rdm.nextInt(5))));
            }else {
                i--;
            }
        }
    }
    public static void generateMezzoTratta(MezzoDAO x, TrattaDAO y) {
        for (int i = 0; i < 10; i++) {
            long n = rdm.nextLong(174, 190);
            Tratta u = y.getById(n);
            if (u != null) {
                if (rdm.nextInt(2) == 1){
                    Mezzo mezzo = new Autobus(LocalDate.now(),Servizio.SERVIZIO,u,LocalDateTime.now(),LocalDateTime.now().plusHours(1),100);
                    x.saveTransport(mezzo);
                } else {
                    Mezzo mezzo1 = new Tram(LocalDate.now(),Servizio.SERVIZIO,u,LocalDateTime.now(),LocalDateTime.now().plusHours(1),400);
                    x.saveTransport(mezzo1);
                };
            } else {
                i--;
            }
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

        int year = rdm.nextInt(2023, 2024);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;
    }

    public static void makeCard(TesseraDAO x, UtenteDAO y){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci ID utente:");
        long id = scanner.nextLong();
        Utente u = y.getById(id);
        if (u != null && x.findlastCard(id) == null){
            x.saveDb(new Tessera(LocalDate.now(), u));
            Tessera t = x.findlastCard(id);
            if (t != null){
                u.setNumero_tessera(t);
                y.saveDb(u);
            }
        }else {
            System.out.println("Sei già tesserato");
        }
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

    public static void createTicketRivenditore(BigliettoDAO x, EmissioneDAO y, TesseraDAO z, UtenteDAO u){
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
                        System.out.println("Tessera valida");
                        makeSeasonTicket(x,e,t);
                    }
                }
            }else {
                System.out.println("vuoi tesserarti?");
                str = scanner.nextLine();
                if (str.equals("si")){
                    makeCard(z, u);
                }else {
                    System.out.println("Biglietto erogato");
                    x.saveBiglietto(new Biglietto(LocalDate.now(), e));
                }
            }
        }else {
            System.out.println("Rivenditore non trovato");
        }

    }

    public static boolean checkExpiration(Tessera y){
        if (LocalDate.now().isAfter(y.getData_scadenza())){
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

    public static void validationSeasonTicket(UtenteDAO x, TesseraDAO y, BigliettoDAO z){
        Scanner scanner = new Scanner(System.in);
        String str = "";
        System.out.println("Inserire id utente");
        long id = scanner.nextLong();
        Utente utente = x.getById(id);
        if (utente != null && utente.getNumero_tessera() != null){
            System.out.println("Controllo tessera");
            Tessera tessera = y.getById(utente.getNumero_tessera().getId());
            if (tessera != null){
                List<Biglietto> abbonamento = z.getItAndCheckExistence(tessera.getId());
                if (!abbonamento.isEmpty()){
                    Biglietto ab = abbonamento.get(0);
                    Periodicita periodo = ((Abbonamento)ab).getPeriodicita();
                    LocalDate dayA = ab.getData();
                    LocalDate dayC = LocalDate.now();
                    if (periodo.equals(Periodicita.SETTIMANALE)){
                        if (dayA.plusDays(7).equals(dayC) || dayC.isAfter(dayA.plusDays(7)) ){
                            System.out.println("Abbonamento non valido" + "\n" + "vuoi rinnovarlo");
                            Scanner risp = new Scanner(System.in);
                            str = risp.nextLine();
                            if (str.equals("si")){
                                ab.setData(LocalDate.now());
                                z.saveBiglietto(ab);
                            }
                        }else {
                            System.out.println("Abbonamento valido");
                        }
                    }else {
                        if (dayA.plusMonths(1).equals(dayC) || dayC.isAfter(dayA.plusMonths(1))){
                            System.out.println("Abbonamento non valido" + "\n" + "vuoi rinnovarlo");
                            Scanner risp = new Scanner(System.in);
                            str = risp.nextLine();
                            if (str.equals("si")){
                                ab.setData(LocalDate.now());
                                z.saveBiglietto(ab);
                            }
                        }
                    }
                }else {
                    System.out.println("Non hai abbonamenti");
                }
            }
        }else {
            System.out.println("Non sei tesserato");
        }
    }


}
