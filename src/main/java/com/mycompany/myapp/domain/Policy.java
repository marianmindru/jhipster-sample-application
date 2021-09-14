package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Policy.
 */
@Entity
@Table(name = "policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numar_polita", nullable = false)
    private String numarPolita;

    @NotNull
    @Column(name = "serie_polita", nullable = false)
    private String seriePolita;

    @NotNull
    @Column(name = "cod_oferta", nullable = false, unique = true)
    private String codOferta;

    @Column(name = "data_start")
    private LocalDate dataStart;

    @Column(name = "data_end")
    private LocalDate dataEnd;

    @Column(name = "perioada")
    private Integer perioada;

    @Size(max = 10)
    @Column(name = "id_produs", length = 10)
    private String idProdus;

    @Size(max = 10)
    @Column(name = "valuta", length = 10)
    private String valuta;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    @Column(name = "suma_plata", precision = 21, scale = 2)
    private BigDecimal sumaPlata;

    @Column(name = "id_agent")
    private String idAgent;

    @JsonIgnoreProperties(value = { "transactions", "client" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Offer offer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "offers", "policies", "transactions" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Policy id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumarPolita() {
        return this.numarPolita;
    }

    public Policy numarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
        return this;
    }

    public void setNumarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
    }

    public String getSeriePolita() {
        return this.seriePolita;
    }

    public Policy seriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
        return this;
    }

    public void setSeriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Policy codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public LocalDate getDataStart() {
        return this.dataStart;
    }

    public Policy dataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
        return this;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataEnd() {
        return this.dataEnd;
    }

    public Policy dataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
        return this;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public Integer getPerioada() {
        return this.perioada;
    }

    public Policy perioada(Integer perioada) {
        this.perioada = perioada;
        return this;
    }

    public void setPerioada(Integer perioada) {
        this.perioada = perioada;
    }

    public String getIdProdus() {
        return this.idProdus;
    }

    public Policy idProdus(String idProdus) {
        this.idProdus = idProdus;
        return this;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Policy valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public BigDecimal getSumaPlata() {
        return this.sumaPlata;
    }

    public Policy sumaPlata(BigDecimal sumaPlata) {
        this.sumaPlata = sumaPlata;
        return this;
    }

    public void setSumaPlata(BigDecimal sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return this.idAgent;
    }

    public Policy idAgent(String idAgent) {
        this.idAgent = idAgent;
        return this;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public Policy offer(Offer offer) {
        this.setOffer(offer);
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Client getClient() {
        return this.client;
    }

    public Policy client(Client client) {
        this.setClient(client);
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Policy)) {
            return false;
        }
        return id != null && id.equals(((Policy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Policy{" +
            "id=" + getId() +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStart='" + getDataStart() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", perioada=" + getPerioada() +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata=" + getSumaPlata() +
            ", idAgent='" + getIdAgent() + "'" +
            "}";
    }
}
