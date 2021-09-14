package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Asigurat;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "cnp_cui_client", length = 50, nullable = false)
    private String cnpCuiClient;

    @NotNull
    @Size(max = 50)
    @Column(name = "nume_asigurat", length = 50, nullable = false)
    private String numeAsigurat;

    @Column(name = "prenume_asigurat")
    private String prenumeAsigurat;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_asigurat")
    private Asigurat tipAsigurat;

    @Size(max = 30)
    @Column(name = "telefon", length = 30)
    private String telefon;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_identifier")
    private String userIdentifier;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transactions", "client" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offer", "client" }, allowSetters = true)
    private Set<Policy> policies = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "offer" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client id(Long id) {
        this.id = id;
        return this;
    }

    public String getCnpCuiClient() {
        return this.cnpCuiClient;
    }

    public Client cnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
        return this;
    }

    public void setCnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
    }

    public String getNumeAsigurat() {
        return this.numeAsigurat;
    }

    public Client numeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
        return this;
    }

    public void setNumeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
    }

    public String getPrenumeAsigurat() {
        return this.prenumeAsigurat;
    }

    public Client prenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
        return this;
    }

    public void setPrenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
    }

    public Asigurat getTipAsigurat() {
        return this.tipAsigurat;
    }

    public Client tipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
        return this;
    }

    public void setTipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Client telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserIdentifier() {
        return this.userIdentifier;
    }

    public Client userIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
        return this;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public Client offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Client addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setClient(this);
        return this;
    }

    public Client removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setClient(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setClient(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setClient(this));
        }
        this.offers = offers;
    }

    public Set<Policy> getPolicies() {
        return this.policies;
    }

    public Client policies(Set<Policy> policies) {
        this.setPolicies(policies);
        return this;
    }

    public Client addPolicy(Policy policy) {
        this.policies.add(policy);
        policy.setClient(this);
        return this;
    }

    public Client removePolicy(Policy policy) {
        this.policies.remove(policy);
        policy.setClient(null);
        return this;
    }

    public void setPolicies(Set<Policy> policies) {
        if (this.policies != null) {
            this.policies.forEach(i -> i.setClient(null));
        }
        if (policies != null) {
            policies.forEach(i -> i.setClient(this));
        }
        this.policies = policies;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public Client transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public Client addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setClient(this);
        return this;
    }

    public Client removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setClient(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setClient(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setClient(this));
        }
        this.transactions = transactions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", cnpCuiClient='" + getCnpCuiClient() + "'" +
            ", numeAsigurat='" + getNumeAsigurat() + "'" +
            ", prenumeAsigurat='" + getPrenumeAsigurat() + "'" +
            ", tipAsigurat='" + getTipAsigurat() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", userIdentifier='" + getUserIdentifier() + "'" +
            "}";
    }
}
