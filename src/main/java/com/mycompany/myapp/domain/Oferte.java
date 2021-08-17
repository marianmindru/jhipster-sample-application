package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StareOferta;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Oferte.
 */
@Entity
@Table(name = "oferte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Oferte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_oferta")
    private String codOferta;

    @Column(name = "data_start_polita")
    private Instant dataStartPolita;

    @Column(name = "data_end")
    private Instant dataEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "stare_oferta")
    private StareOferta stareOferta;

    @Column(name = "id_produs")
    private String idProdus;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "suma_plata")
    private String sumaPlata;

    @Column(name = "id_agent")
    private String idAgent;

    @Column(name = "id_user")
    private Integer idUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ofertes", "polites", "tranzactiis" }, allowSetters = true)
    private Users users;

    @JsonIgnoreProperties(value = { "oferte", "polite", "users" }, allowSetters = true)
    @OneToOne(mappedBy = "oferte")
    private Tranzactii tranzactii;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Oferte id(Long id) {
        this.id = id;
        return this;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Oferte codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public Instant getDataStartPolita() {
        return this.dataStartPolita;
    }

    public Oferte dataStartPolita(Instant dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
        return this;
    }

    public void setDataStartPolita(Instant dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
    }

    public Instant getDataEnd() {
        return this.dataEnd;
    }

    public Oferte dataEnd(Instant dataEnd) {
        this.dataEnd = dataEnd;
        return this;
    }

    public void setDataEnd(Instant dataEnd) {
        this.dataEnd = dataEnd;
    }

    public StareOferta getStareOferta() {
        return this.stareOferta;
    }

    public Oferte stareOferta(StareOferta stareOferta) {
        this.stareOferta = stareOferta;
        return this;
    }

    public void setStareOferta(StareOferta stareOferta) {
        this.stareOferta = stareOferta;
    }

    public String getIdProdus() {
        return this.idProdus;
    }

    public Oferte idProdus(String idProdus) {
        this.idProdus = idProdus;
        return this;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Oferte valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getSumaPlata() {
        return this.sumaPlata;
    }

    public Oferte sumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
        return this;
    }

    public void setSumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return this.idAgent;
    }

    public Oferte idAgent(String idAgent) {
        this.idAgent = idAgent;
        return this;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public Oferte idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Users getUsers() {
        return this.users;
    }

    public Oferte users(Users users) {
        this.setUsers(users);
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Tranzactii getTranzactii() {
        return this.tranzactii;
    }

    public Oferte tranzactii(Tranzactii tranzactii) {
        this.setTranzactii(tranzactii);
        return this;
    }

    public void setTranzactii(Tranzactii tranzactii) {
        if (this.tranzactii != null) {
            this.tranzactii.setOferte(null);
        }
        if (tranzactii != null) {
            tranzactii.setOferte(this);
        }
        this.tranzactii = tranzactii;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Oferte)) {
            return false;
        }
        return id != null && id.equals(((Oferte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Oferte{" +
            "id=" + getId() +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStartPolita='" + getDataStartPolita() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", stareOferta='" + getStareOferta() + "'" +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata='" + getSumaPlata() + "'" +
            ", idAgent='" + getIdAgent() + "'" +
            ", idUser=" + getIdUser() +
            "}";
    }
}
