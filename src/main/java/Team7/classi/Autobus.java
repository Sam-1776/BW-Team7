package Team7.classi;

import Team7.superclassi.Mezzo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("autobus")
public class Autobus extends Mezzo {
    private int capienzaBus;

    public Autobus(LocalDate periodo, Servizio servizio, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo, int capienzaBus) {
        super(periodo, servizio, tratta, partenza, arrivo);
        this.capienzaBus = capienzaBus;
    }

    public Autobus(){

    }

    public int getCapienze() {
        return capienzaBus;
    }

    public void setCapienze(int capienze) {
        this.capienzaBus = capienze;
    }
}
