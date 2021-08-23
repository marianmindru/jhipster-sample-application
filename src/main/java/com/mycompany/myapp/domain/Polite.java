package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Polite.
 */
@Entity
@Table(name = "polite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Polite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numar_polita", nullable = false)
    private String numarPolita;

    @Column(name = "serie_polita")
    private String seriePolita;

    @Column(name = "cod_oferta")
    private String codOferta;

    @Column(name = "data_start")
    private LocalDate dataStart;

    @Column(name = "data_end")
    private LocalDate dataEnd;

    @Column(name = "perioada")
    private Integer perioada;

    @Column(name = "id_produs")
    private String idProdus;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "suma_plata")
    private String sumaPlata;

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

    public Polite id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumarPolita() {
        return this.numarPolita;
    }

    public Polite numarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
        return this;
    }

    public void setNumarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
    }

    public String getSeriePolita() {
        return this.seriePolita;
    }

    public Polite seriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
        return this;
    }

    public void setSeriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Polite codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public LocalDate getDataStart() {
        return this.dataStart;
    }

    public Polite dataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
        return this;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataEnd() {
        return this.dataEnd;
    }

    public Polite dataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
        return this;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public Integer getPerioada() {
        return this.perioada;
    }

    public Polite perioada(Integer perioada) {
        this.perioada = perioada;
        return this;
    }

    public void setPerioada(Integer perioada) {
        this.perioada = perioada;
    }

    public String getIdProdus() {
        return this.idProdus;
    }

    public Polite idProdus(String idProdus) {
        this.idProdus = idProdus;
        return this;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Polite valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getSumaPlata() {
        return this.sumaPlata;
    }

    public Polite sumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
        return this;
    }

    public void setSumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return this.idAgent;
    }

    public Polite idAgent(String idAgent) {
        this.idAgent = idAgent;
        return this;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public Clients getUser() {
        return this.user;
    }

    public Polite user(Clients clients) {
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
        if (!(o instanceof Polite)) {
            return false;
        }
        return id != null && id.equals(((Polite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Polite{" +
            "id=" + getId() +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStart='" + getDataStart() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", perioada=" + getPerioada() +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata='" + getSumaPlata() + "'" +
            ", idAgent='" + getIdAgent() + "'" +
            "}";
    }
}
