package Team7.classi;

import Team7.superclassi.Mezzo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Tratta {
    @Id
    @GeneratedValue
    private long id ;
    private String zonaPartenza;
    private String capolinea;

    private double tempoMedio;

    @OneToMany(mappedBy = "tratta")
    private  List<Mezzo> listaMezzi;

    @OneToMany(mappedBy = "tratta")
    private List<Tappa> tappa;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, double tempoMedio) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoMedio = tempoMedio;
    }


}
