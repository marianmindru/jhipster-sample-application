package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StareOferta;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_oferta", nullable = false, unique = true)
    private String codOferta;

    @Column(name = "data_start_polita")
    private LocalDate dataStartPolita;

    @Column(name = "data_end")
    private LocalDate dataEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "stare_oferta")
    private StareOferta stareOferta;

    @Size(max = 10)
    @Column(name = "id_produs", length = 10)
    private String idProdus;

    @Size(max = 10)
    @Column(name = "valuta", length = 10)
    private String valuta;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    @Column(name = "suma_plata")
    private Double sumaPlata;

    @Column(name = "id_agent")
    private String idAgent;

    @OneToMany(mappedBy = "offer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "policy", "client", "offer" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

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

    public Offer id(Long id) {
        this.id = id;
        return this;
    }

    public String getCodOferta() {
        return this.codOferta;
    }

    public Offer codOferta(String codOferta) {
        this.codOferta = codOferta;
        return this;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public LocalDate getDataStartPolita() {
        return this.dataStartPolita;
    }

    public Offer dataStartPolita(LocalDate dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
        return this;
    }

    public void setDataStartPolita(LocalDate dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
    }

    public LocalDate getDataEnd() {
        return this.dataEnd;
    }

    public Offer dataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
        return this;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public StareOferta getStareOferta() {
        return this.stareOferta;
    }

    public Offer stareOferta(StareOferta stareOferta) {
        this.stareOferta = stareOferta;
        return this;
    }

    public void setStareOferta(StareOferta stareOferta) {
        this.stareOferta = stareOferta;
    }

    public String getIdProdus() {
        return this.idProdus;
    }

    public Offer idProdus(String idProdus) {
        this.idProdus = idProdus;
        return this;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Offer valuta(String valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Double getSumaPlata() {
        return this.sumaPlata;
    }

    public Offer sumaPlata(Double sumaPlata) {
        this.sumaPlata = sumaPlata;
        return this;
    }

    public void setSumaPlata(Double sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return this.idAgent;
    }

    public Offer idAgent(String idAgent) {
        this.idAgent = idAgent;
        return this;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public Offer transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public Offer addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setOffer(this);
        return this;
    }

    public Offer removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setOffer(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setOffer(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setOffer(this));
        }
        this.transactions = transactions;
    }

    public Client getClient() {
        return this.client;
    }

    public Offer client(Client client) {
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
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
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
