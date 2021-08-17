package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StatusPlata;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tranzactii.
 */
@Entity
@Table(name = "tranzactii")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tranzactii implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_oferta")
    private String codOferta;

    @Column(name = "numar_polita")
    private String numarPolita;

    @Column(name = "serie_polita")
    private String seriePolita;

    @Column(name = "suma_platita")
    private String sumaPlatita;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_plata")
    private StatusPlata statusPlata;

    @Column(name = "data_plata")
    private Instant dataPlata;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "referinta_ing")
    private String referintaIng;

    @JsonIgnoreProperties(value = { "users", "tranzactii" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Oferte oferte;

    @JsonIgnoreProperties(value = { "users", "tranzactii" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Polite polite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ofertes", "polites", "tranzactiis" }, allowSetters = true)
    private Users users;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tranzactii id(Long id) {
        this.id = id;
        return this;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Tranzactii codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public String getNumarPolita() {
        return this.numarPolita;
    }

    public Tranzactii numarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
        return this;
    }

    public void setNumarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
    }

    public String getSeriePolita() {
        return this.seriePolita;
    }

    public Tranzactii seriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
        return this;
    }

    public void setSeriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
    }

    public String getSumaPlatita() {
        return this.sumaPlatita;
    }

    public Tranzactii sumaPlatita(String sumaPlatita) {
        this.sumaPlatita = sumaPlatita;
        return this;
    }

    public void setSumaPlatita(String sumaPlatita) {
        this.sumaPlatita = sumaPlatita;
    }

    public StatusPlata getStatusPlata() {
        return this.statusPlata;
    }

    public Tranzactii statusPlata(StatusPlata statusPlata) {
        this.statusPlata = statusPlata;
        return this;
    }

    public void setStatusPlata(StatusPlata statusPlata) {
        this.statusPlata = statusPlata;
    }

    public Instant getDataPlata() {
        return this.dataPlata;
    }

    public Tranzactii dataPlata(Instant dataPlata) {
        this.dataPlata = dataPlata;
        return this;
    }

    public void setDataPlata(Instant dataPlata) {
        this.dataPlata = dataPlata;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Tranzactii valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public Tranzactii idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getReferintaIng() {
        return this.referintaIng;
    }

    public Tranzactii referintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
        return this;
    }

    public void setReferintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
    }

    public Oferte getOferte() {
        return this.oferte;
    }

    public Tranzactii oferte(Oferte oferte) {
        this.setOferte(oferte);
        return this;
    }

    public void setOferte(Oferte oferte) {
        this.oferte = oferte;
    }

    public Polite getPolite() {
        return this.polite;
    }

    public Tranzactii polite(Polite polite) {
        this.setPolite(polite);
        return this;
    }

    public void setPolite(Polite polite) {
        this.polite = polite;
    }

    public Users getUsers() {
        return this.users;
    }

    public Tranzactii users(Users users) {
        this.setUsers(users);
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tranzactii)) {
            return false;
        }
        return id != null && id.equals(((Tranzactii) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tranzactii{" +
            "id=" + getId() +
            ", codOferta='" + getCodOferta() + "'" +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", sumaPlatita='" + getSumaPlatita() + "'" +
            ", statusPlata='" + getStatusPlata() + "'" +
            ", dataPlata='" + getDataPlata() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", idUser=" + getIdUser() +
            ", referintaIng='" + getReferintaIng() + "'" +
            "}";
    }
}
