package Team7.classi;


import Team7.superclassi.Mezzo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("tram")
public class Tram extends Mezzo {

    private int capienzaTram;

    public Tram(LocalDate periodo, Servizio servizio, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo, int capienzaTram) {
        super(periodo, servizio, tratta, partenza, arrivo);
        this.capienzaTram = capienzaTram;
    }

    public Tram(){

    }

    public int getCapienza() {
        return capienzaTram;
    }

    public void setCapienza(int capienza) {
        this.capienzaTram = capienza;
    }

}
