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
 * A Clients.
 */
@Entity
@Table(name = "clients")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Clients implements Serializable {

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

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_identifier")
    private String userIdentifier;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Oferte> ofertes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Polite> polites = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "oferta", "user" }, allowSetters = true)
    private Set<Tranzactii> tranzactiis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clients id(Long id) {
        this.id = id;
        return this;
    }

    public String getCnpCuiClient() {
        return this.cnpCuiClient;
    }

    public Clients cnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
        return this;
    }

    public void setCnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
    }

    public String getNumeAsigurat() {
        return this.numeAsigurat;
    }

    public Clients numeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
        return this;
    }

    public void setNumeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
    }

    public String getPrenumeAsigurat() {
        return this.prenumeAsigurat;
    }

    public Clients prenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
        return this;
    }

    public void setPrenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
    }

    public Asigurat getTipAsigurat() {
        return this.tipAsigurat;
    }

    public Clients tipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
        return this;
    }

    public void setTipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Clients telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return this.email;
    }

    public Clients email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserIdentifier() {
        return this.userIdentifier;
    }

    public Clients userIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
        return this;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Set<Oferte> getOfertes() {
        return this.ofertes;
    }

    public Clients ofertes(Set<Oferte> ofertes) {
        this.setOfertes(ofertes);
        return this;
    }

    public Clients addOferte(Oferte oferte) {
        this.ofertes.add(oferte);
        oferte.setUser(this);
        return this;
    }

    public Clients removeOferte(Oferte oferte) {
        this.ofertes.remove(oferte);
        oferte.setUser(null);
        return this;
    }

    public void setOfertes(Set<Oferte> ofertes) {
        if (this.ofertes != null) {
            this.ofertes.forEach(i -> i.setUser(null));
        }
        if (ofertes != null) {
            ofertes.forEach(i -> i.setUser(this));
        }
        this.ofertes = ofertes;
    }

    public Set<Polite> getPolites() {
        return this.polites;
    }

    public Clients polites(Set<Polite> polites) {
        this.setPolites(polites);
        return this;
    }

    public Clients addPolite(Polite polite) {
        this.polites.add(polite);
        polite.setUser(this);
        return this;
    }

    public Clients removePolite(Polite polite) {
        this.polites.remove(polite);
        polite.setUser(null);
        return this;
    }

    public void setPolites(Set<Polite> polites) {
        if (this.polites != null) {
            this.polites.forEach(i -> i.setUser(null));
        }
        if (polites != null) {
            polites.forEach(i -> i.setUser(this));
        }
        this.polites = polites;
    }

    public Set<Tranzactii> getTranzactiis() {
        return this.tranzactiis;
    }

    public Clients tranzactiis(Set<Tranzactii> tranzactiis) {
        this.setTranzactiis(tranzactiis);
        return this;
    }

    public Clients addTranzactii(Tranzactii tranzactii) {
        this.tranzactiis.add(tranzactii);
        tranzactii.setUser(this);
        return this;
    }

    public Clients removeTranzactii(Tranzactii tranzactii) {
        this.tranzactiis.remove(tranzactii);
        tranzactii.setUser(null);
        return this;
    }

    public void setTranzactiis(Set<Tranzactii> tranzactiis) {
        if (this.tranzactiis != null) {
            this.tranzactiis.forEach(i -> i.setUser(null));
        }
        if (tranzactiis != null) {
            tranzactiis.forEach(i -> i.setUser(this));
        }
        this.tranzactiis = tranzactiis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clients)) {
            return false;
        }
        return id != null && id.equals(((Clients) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clients{" +
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
