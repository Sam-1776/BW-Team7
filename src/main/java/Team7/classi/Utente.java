package Team7.classi;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class Utente {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private int data_nascita;

    @OneToOne
    @JoinColumn(name = "tessera_id")
    private Tessera numero_tessera;

    public Utente(String name, String surname, int data_nascita) {
        this.name = name;
        this.surname = surname;
        this.data_nascita = data_nascita;
    }

    public Utente() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(int data_nascita) {
        this.data_nascita = data_nascita;
    }

    public Tessera getNumero_tessera() {
        return numero_tessera;
    }

    public void setNumero_tessera(Tessera numero_tessera) {
        this.numero_tessera = numero_tessera;
    }

    public String toStringNoCard() {
        return "Utente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", data_nascita=" + data_nascita +
                '}';
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", data_nascita=" + data_nascita +
                ", numero_tessera=" + numero_tessera +
                '}';
    }
}
