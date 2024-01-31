package Team7.classi;

import Team7.superclassi.Mezzo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
public class Tratta {
    @Id
    @GeneratedValue
    private long id;
    private String zonaPartenza;
    private String capolinea;

    private double tempoMedio;

    @OneToMany(mappedBy = "tratta")
    @ToString.Exclude
    private List<Mezzo> listaMezzi;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "tratta")
    @Column(name = "tappa")
    @ToString.Exclude
    private List<Tappa> tappe;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, double tempoMedio) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoMedio = tempoMedio;
        this.tappe = new ArrayList<>();
        this.listaMezzi = new ArrayList<>();
    }

    public Duration calcoloTempoPrevisto() {
        if (tappe == null || tappe.isEmpty()) {
            return Duration.ZERO;
        }
        Tappa tappa1 = tappe.get(0);
        Tappa tappaFinale = tappe.get(tappe.size() - 1);
        return Duration.between(tappa1.getArrivo(), tappaFinale.getArrivo());
    }


}
