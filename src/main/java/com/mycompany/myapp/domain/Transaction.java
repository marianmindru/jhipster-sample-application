package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StatusPlata;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_oferta", nullable = false)
    private String codOferta;

    @Column(name = "numar_polita")
    private String numarPolita;

    @Column(name = "serie_polita")
    private String seriePolita;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    @Column(name = "suma_platita", precision = 21, scale = 2)
    private BigDecimal sumaPlatita;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_plata")
    private StatusPlata statusPlata;

    @Column(name = "data_plata")
    private LocalDate dataPlata;

    @Size(max = 10)
    @Column(name = "valuta", length = 10)
    private String valuta;

    @NotNull
    @Column(name = "referinta_ing", nullable = false)
    private String referintaIng;

    @ManyToOne
    @JsonIgnoreProperties(value = { "offers", "policies", "transactions" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "transactions", "client" }, allowSetters = true)
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction id(Long id) {
        this.id = id;
        return this;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Transaction codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public String getNumarPolita() {
        return this.numarPolita;
    }

    public Transaction numarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
        return this;
    }

    public void setNumarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
    }

    public String getSeriePolita() {
        return this.seriePolita;
    }

    public Transaction seriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
        return this;
    }

    public void setSeriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
    }

    public BigDecimal getSumaPlatita() {
        return this.sumaPlatita;
    }

    public Transaction sumaPlatita(BigDecimal sumaPlatita) {
        this.sumaPlatita = sumaPlatita;
        return this;
    }

    public void setSumaPlatita(BigDecimal sumaPlatita) {
        this.sumaPlatita = sumaPlatita;
    }

    public StatusPlata getStatusPlata() {
        return this.statusPlata;
    }

    public Transaction statusPlata(StatusPlata statusPlata) {
        this.statusPlata = statusPlata;
        return this;
    }

    public void setStatusPlata(StatusPlata statusPlata) {
        this.statusPlata = statusPlata;
    }

    public LocalDate getDataPlata() {
        return this.dataPlata;
    }

    public Transaction dataPlata(LocalDate dataPlata) {
        this.dataPlata = dataPlata;
        return this;
    }

    public void setDataPlata(LocalDate dataPlata) {
        this.dataPlata = dataPlata;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Transaction valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getReferintaIng() {
        return this.referintaIng;
    }

    public Transaction referintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
        return this;
    }

    public void setReferintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
    }

    public Client getClient() {
        return this.client;
    }

    public Transaction client(Client client) {
        this.setClient(client);
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public Transaction offer(Offer offer) {
        this.setOffer(offer);
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", codOferta='" + getCodOferta() + "'" +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", sumaPlatita=" + getSumaPlatita() +
            ", statusPlata='" + getStatusPlata() + "'" +
            ", dataPlata='" + getDataPlata() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", referintaIng='" + getReferintaIng() + "'" +
            "}";
    }
}
