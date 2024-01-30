package Team7.superclassi;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Biglietto {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    private LocalDate data;

    private LocalDateTime timbro;

    @ManyToOne
    @JoinColumn(name = "emissione_id")
    private Emissione_Biglietti emissioneBiglietti;

    public Biglietto() {
    }

    public Biglietto(LocalDate data, Emissione_Biglietti emissioneBiglietti) {
        this.data = data;
        this.emissioneBiglietti = emissioneBiglietti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getTimbro() {
        return timbro;
    }

    public void setTimbro(LocalDateTime timbro) {
        this.timbro = timbro;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "mezzo=" + mezzo +
                ", data=" + data +
                ", timbro=" + timbro +
                ", emissioneBiglietti=" + emissioneBiglietti +
                '}';
    }
}
