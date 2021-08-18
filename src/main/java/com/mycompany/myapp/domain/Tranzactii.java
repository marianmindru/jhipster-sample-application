package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.StatusPlata;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "id_oferta")
    private Long idOferta;

    @Column(name = "numar_polita")
    private String numarPolita;

    @Column(name = "serie_polita")
    private String seriePolita;

    @Column(name = "id_polita")
    private Long idPolita;

    @Column(name = "suma_platita")
    private String sumaPlatita;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_plata")
    private StatusPlata statusPlata;

    @Column(name = "data_plata")
    private LocalDate dataPlata;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "referinta_ing")
    private String referintaIng;

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

    public Long getIdOferta() {
        return this.idOferta;
    }

    public Tranzactii idOferta(Long idOferta) {
        this.idOferta = idOferta;
        return this;
    }

    public void setIdOferta(Long idOferta) {
        this.idOferta = idOferta;
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

    public Long getIdPolita() {
        return this.idPolita;
    }

    public Tranzactii idPolita(Long idPolita) {
        this.idPolita = idPolita;
        return this;
    }

    public void setIdPolita(Long idPolita) {
        this.idPolita = idPolita;
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

    public LocalDate getDataPlata() {
        return this.dataPlata;
    }

    public Tranzactii dataPlata(LocalDate dataPlata) {
        this.dataPlata = dataPlata;
        return this;
    }

    public void setDataPlata(LocalDate dataPlata) {
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

    public Long getIdUser() {
        return this.idUser;
    }

    public Tranzactii idUser(Long idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Long idUser) {
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
            ", idOferta=" + getIdOferta() +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", idPolita=" + getIdPolita() +
            ", sumaPlatita='" + getSumaPlatita() + "'" +
            ", statusPlata='" + getStatusPlata() + "'" +
            ", dataPlata='" + getDataPlata() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", idUser=" + getIdUser() +
            ", referintaIng='" + getReferintaIng() + "'" +
            "}";
    }
}
