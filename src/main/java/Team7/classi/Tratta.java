package Team7.classi;

import Team7.superclassi.Mezzo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Tratta {
    @Id
    @GeneratedValue
    private long id ;
    private String zonaPartenza;
    private String capolinea;

    private double tempoMedio;

    @OneToMany(mappedBy = "tratta")
    private  List<Mezzo> listaMezzi;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, double tempoMedio, List<Mezzo> listaMezzi) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoMedio = tempoMedio;
        this.listaMezzi = listaMezzi;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public double getTempoMedio() {
        return tempoMedio;
    }

    public void setTempoMedio(double tempoMedio) {
        this.tempoMedio = tempoMedio;
    }

    public List<Mezzo> getListaMezzi() {
        return listaMezzi;
    }

    public void setListaMezzi(List<Mezzo> listaMezzi) {
        this.listaMezzi = listaMezzi;
    }
}
