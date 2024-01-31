package Team7.classi;

import Team7.superclassi.Mezzo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Entity
@Getter
@Setter
public class Tratta {
    @Id
    @GeneratedValue
    private long id;
    private String zonaPartenza;
    private String capolinea;

    private double tempoMedio;

    @OneToMany(mappedBy = "tratta")
    private List<Mezzo> listaMezzi;

    @OneToMany(mappedBy = "tratta")
    @Column(name = "tappa")
    private List<Tappa> tappe;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, double tempoMedio) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoMedio = tempoMedio;
    }

    public Duration calcoloTempoPrevisto() {
        Tappa tappa1 = tappe.get(0);
        Tappa tappaFinale = tappe.get(tappe.size() - 1);
        return Duration.between(tappa1.getArrivo(), tappaFinale.getArrivo());
    }


}
