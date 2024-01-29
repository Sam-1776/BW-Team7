package Team7.classi;


import Team7.superclassi.Mezzo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("tram")
public class Tram extends Mezzo {
    @Id
    @GeneratedValue
    private long id;
    private int capienza;

    public Tram(LocalDate periodo, Servizio servizio, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo, int capienza) {
        super(periodo, servizio, tratta, partenza, arrivo);
        this.capienza = capienza;
    }

    public Tram(){

    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

}
