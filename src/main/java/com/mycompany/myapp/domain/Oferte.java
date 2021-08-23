package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StareOferta;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Column(name = "cod_oferta", nullable = false)
    private String codOferta;

    @Column(name = "data_start_polita")
    private LocalDate dataStartPolita;

    @Column(name = "data_end")
    private LocalDate dataEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "stare_oferta")
    private StareOferta stareOferta;

    @Column(name = "id_produs")
    private String idProdus;

    @Column(name = "valuta")
    private String valuta;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    @Column(name = "suma_plata")
    private Double sumaPlata;

    @Column(name = "id_agent")
    private String idAgent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ofertes", "polites", "tranzactiis" }, allowSetters = true)
    private Clients user;

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

    public LocalDate getDataStartPolita() {
        return this.dataStartPolita;
    }

    public Oferte dataStartPolita(LocalDate dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
        return this;
    }

    public void setDataStartPolita(LocalDate dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
    }

    public LocalDate getDataEnd() {
        return this.dataEnd;
    }

    public Oferte dataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
        return this;
    }

    public void setDataEnd(LocalDate dataEnd) {
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

    public Double getSumaPlata() {
        return this.sumaPlata;
    }

    public Oferte sumaPlata(Double sumaPlata) {
        this.sumaPlata = sumaPlata;
        return this;
    }

    public void setSumaPlata(Double sumaPlata) {
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

    public Clients getUser() {
        return this.user;
    }

    public Oferte user(Clients clients) {
        this.setUser(clients);
        return this;
    }

    public void setUser(Clients clients) {
        this.user = clients;
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
            ", sumaPlata=" + getSumaPlata() +
            ", idAgent='" + getIdAgent() + "'" +
            "}";
    }
}
