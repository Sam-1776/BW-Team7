package Team7;

import Team7.classi.Manutenzione;
import Team7.dao.*;
import Team7.superclassi.Emissione_Biglietti;
import Team7.utilities.Metodi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


public class Application {

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestione_trasporti");
    public static Metodi metodi = new Metodi();

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
        //SERVIZI DELL'APPLICAZIONE

        System.out.println("Seleziona: \n" + "1- Generazione DB \n" + "2- Operazioni Biglietti \n" + "3- Operazione sui Mezzi \n" + "4- Operazioni sulle Tratte \n" + "0-ESCI");
        int scelta = scanner.nextInt();
        boolean ciclo = true;

            switch (scelta){
                case 1:
                    // genera automaticamente utenti casuali
                    metodi.generateUserDb(utenteDAO);
                    // genera automaticamente card per degli utenti presi casualmente
                    metodi.generateUserCard(tesseraDAO,utenteDAO);
                    // genera automaticamente distributori e punti vendita per i biglietti
                    metodi.generateEmitter(emissioneDAO);
                    // genera automaticamente tratte per i mezzi
                    metodi.generateTrattaDb(trattaDao);
                    //genera automaticamente tappe delle tratte
                   metodi. generateTappaTratta(trattaDao);
                    // genera automaticamente dei mezzi
                    metodi.generateMezzoTratta(mezzoDAO,trattaDao);
                    break;
                case 2:
                    // permette di testare tutte le funzioni dei biglietti abbonamenti e tessere
                    functionOnTicket(utenteDAO, bigliettoDAO, tesseraDAO, emissioneDAO);
                    break;
                case 3:
                    // permette di testare tutte le funzioni dei mezzi
                    functionOnTransport(mezzoDAO, trattaDao, bigliettoDAO, manutenzioneDAO);
                    break;
                case 4:
                    // permette di testare le funzioni sulle tappe dei mezzi
                    functionOnLap(mezzoDAO, tappaDAO);
                    break;
                default:
                    System.out.println("Arrivederci");
                    break;
            }



    }

    public static void functionOnTicket(UtenteDAO x, BigliettoDAO y, TesseraDAO z, EmissioneDAO e){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleziona: \n" + "1- per andare dal rivenditore \n" +"2- per andare dal distributore \n" + "3- controllo dei biglietti erogati \n" + "4- controllo di tutti i biglietti/abbonamenti totali \n" + "5- controllo validità abbonamento \n" + "0- ESCI");
        int scelta = scanner.nextInt();
        switch (scelta){
            case 1:
                // permette di creare un biglietto o abbonamento da un rivenditore
                metodi.createTicketRivenditore(y,e,z,x);
                scanner.close();
                break;
            case 2:
                // permette di creare un biglietto da distributore
                metodi.createTicket(y,e);
                scanner.close();
                break;
            case 3:
                //Controllo biglietti emessi da un distributore o da un rivenditore:
                System.out.println("Inserisci L' ID del distributore o del rivenditore: ");
                int scanner2= scanner.nextInt();
                Emissione_Biglietti emissione1 = e.getById(scanner2);
                System.out.println(y.getBigliettiPerPuntoDiEmissione(emissione1).size());
                scanner.close();
                break;
            case 4:
                // controlla in numero dei biglietti e abbonamenti totali di un determinato giorno
                Scanner input = new Scanner(System.in);
                System.out.println("Inserire data da controllare");
                String str = input.nextLine();
                LocalDate day = LocalDate.parse(str);
                y.getBigliettiTotali(day).forEach(System.out::println);
                break;
            case 5:
                // serve a controllare la validità di un abbonamento
                metodi.validationSeasonTicket(x, z, y);
            default:
                System.out.println("Arrivederci");
                scanner.close();
                break;
        }
    }

    public static void functionOnTransport(MezzoDAO x, TrattaDAO y, BigliettoDAO z, ManutenzioneDAO m){
        Scanner scanner = new Scanner(System.in);
        long id = 0;
        System.out.println("Seleziona: \n" + "1- Metti in manutenzione un Mezzo \n" + "2- Metti in servizio un Mezzo \n" + "3- Controlla il periodo di manutenzione e servizio di un Mezzo \n" + "4- Timbro biglietto \n" + "5- Controllo biglietti timbrati su un mezzo \n" + "6- Controllo biglietti di un dato giorno \n" + "0- ESCI");
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
                System.out.println("Inserire id tratta");
                long idT = scanner.nextLong();
                x.fineManutenzione(LocalDate.now(),x.getById(id),y.getById(idT));
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

    public static void functionOnLap(MezzoDAO x, TappaDAO y){
        Scanner scanner = new Scanner(System.in);
        long id = 0;
        long idT = 0;
        System.out.println("Seleziona \n" + "1- Mezzo percorre una tappa \n" + "2- Calcolo delle volte che un mezzo percorre una tappa");
        int scelta = scanner.nextInt();
        switch (scelta){
            case 1:
                System.out.println("Inserisci di mezzo");
                id = scanner.nextLong();
                System.out.println("Inserire id tappa");
                idT = scanner.nextLong();
                x.percorriTappa(x.getById(id), y.getById(idT));
                break;
            case 2:
                System.out.println("Inserisci di mezzo");
                id = scanner.nextLong();
                System.out.println("Inserire id tappa");
                idT = scanner.nextLong();
                x.numeroPercorrenzaTappa(x.getById(id), y.getById(idT));
                break;
            default:
                System.out.println("Arrivederci");
                break;
        }
    }



}
