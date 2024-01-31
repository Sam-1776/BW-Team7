package Team7.classi;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "card")
@Getter
@Setter
public class Tessera {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate data;
    private LocalDate data_scadenza;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente nominativo;

    public Tessera(LocalDate data, Utente nominativo) {
        this.data = data;
        this.data_scadenza = data.plusYears(1);
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
