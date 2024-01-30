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

    public Biglietto() {
    }

    public Biglietto(LocalDate data) {
        this.data = data;
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
}
