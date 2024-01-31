package Team7.classi;

import Team7.superclassi.Mezzo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fermata {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;


    @OneToOne
    private Mezzo mezzo;
    @OneToOne
    private Tappa tappa;

    private LocalDateTime orario;



}
