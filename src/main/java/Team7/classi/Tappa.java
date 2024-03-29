package Team7.classi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Tappa {
    @Id
    @GeneratedValue
    private long id;

    private String nome;
    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;

    private LocalDateTime arrivo;

    private LocalDateTime arrivoProssimaTappa;

    public Tappa() {
    }

    public Tappa(String nome, Tratta tratta, LocalDateTime arrivo, LocalDateTime arrivoProssimaTappa) {
        this.nome = nome;
        this.tratta = tratta;
        this.arrivo = arrivo;
        this.arrivoProssimaTappa = arrivoProssimaTappa;
    }
    public Tappa(String nome, LocalDateTime arrivo, LocalDateTime arrivoProssimaTappa) {
        this.nome = nome;
        this.arrivo = arrivo;
        this.arrivoProssimaTappa = arrivoProssimaTappa;
    }




}
