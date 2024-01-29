package Team7.classi;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "card")
public class Tessera {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate data;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente nominativo;

    public Tessera(LocalDate data, Utente nominativo) {
        this.data = data;
        this.nominativo = nominativo;
    }

    public Tessera() {
    }

    public long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Utente getNominativo() {
        return nominativo;
    }

    public void setNominativo(Utente nominativo) {
        this.nominativo = nominativo;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", data=" + data +
                ", nominativo=" + nominativo +
                '}';
    }
}
