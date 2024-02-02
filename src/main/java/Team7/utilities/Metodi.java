package Team7.utilities;

import Team7.classi.*;
import Team7.dao.*;
import Team7.superclassi.Biglietto;
import Team7.superclassi.Emissione_Biglietti;
import Team7.superclassi.Mezzo;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Metodi {

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

    public  void generateUserDb(UtenteDAO x) {
        for (int i = 0; i < 100; i++) {
            x.saveDb(generatoreUser.get());
        }
    }
    public  void generateTrattaDb(TrattaDAO x) {
        for (int i = 0; i < 5; i++) {
            x.saveSection(generateTratta.get());
        }
    }

    public  void generateTappaTratta(TrattaDAO x){
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
    public  void generateMezzoTratta(MezzoDAO x, TrattaDAO y) {
        for (int i = 0; i < 10; i++) {
            long n = rdm.nextLong(174, 190);
            Tratta u = y.getById(n);
            if (u != null) {
                if (rdm.nextInt(2) == 1){
                    Mezzo mezzo = new Autobus(LocalDate.now(), Servizio.SERVIZIO,u,LocalDateTime.now(),LocalDateTime.now().plusHours(1),100);
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

    public  void generateUserCard(TesseraDAO x, UtenteDAO y) {
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

    public  LocalDate generateData() {

        int year = rdm.nextInt(2023, 2024);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;
    }

    public  void makeCard(TesseraDAO x, UtenteDAO y){
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

    public  void generateEmitter(EmissioneDAO x){
        for (int i = 0; i < 50; i++) {
            long y = rdm.nextLong(1, 3);
            if (y == 1){
                x.saveDb(generateDispenser.get());
            }else {
                x.saveDb(generateDealer.get());
            }
        }
    }

    public  void createTicket(BigliettoDAO x, EmissioneDAO y){
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

    public  void createTicketRivenditore(BigliettoDAO x, EmissioneDAO y, TesseraDAO z, UtenteDAO u){
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

    public  boolean checkExpiration(Tessera y){
        if (LocalDate.now().isAfter(y.getData_scadenza())){
            return true;
        }
        return false;
    }

    public  void makeSeasonTicket(BigliettoDAO x, Emissione_Biglietti y, Tessera z){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Che periodo vuoi l'abbonamento");
        String str = scanner.nextLine();
        if (str.equals(Periodicita.SETTIMANALE.toString().toLowerCase())){
            x.saveBiglietto(new Abbonamento(LocalDate.now(), y, Periodicita.SETTIMANALE, z));
        }else {
            x.saveBiglietto(new Abbonamento(LocalDate.now(), y, Periodicita.MENSILE, z));
        }
    }

    public  void validationSeasonTicket(UtenteDAO x, TesseraDAO y, BigliettoDAO z){
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
