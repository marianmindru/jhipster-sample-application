package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Asigurat;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cnp_cui_client")
    private String cnpCuiClient;

    @Column(name = "nume_asigurat")
    private String numeAsigurat;

    @Column(name = "prenume_asigurat")
    private String prenumeAsigurat;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_asigurat")
    private Asigurat tipAsigurat;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "im_code")
    private String imCode;

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "tranzactii" }, allowSetters = true)
    private Set<Oferte> ofertes = new HashSet<>();

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "tranzactii" }, allowSetters = true)
    private Set<Polite> polites = new HashSet<>();

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "oferte", "polite", "users" }, allowSetters = true)
    private Set<Tranzactii> tranzactiis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users id(Long id) {
        this.id = id;
        return this;
    }

    public String getCnpCuiClient() {
        return this.cnpCuiClient;
    }

    public Users cnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
        return this;
    }

    public void setCnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
    }

    public String getNumeAsigurat() {
        return this.numeAsigurat;
    }

    public Users numeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
        return this;
    }

    public void setNumeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
    }

    public String getPrenumeAsigurat() {
        return this.prenumeAsigurat;
    }

    public Users prenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
        return this;
    }

    public void setPrenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
    }

    public Asigurat getTipAsigurat() {
        return this.tipAsigurat;
    }

    public Users tipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
        return this;
    }

    public void setTipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Users telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return this.email;
    }

    public Users email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImCode() {
        return this.imCode;
    }

    public Users imCode(String imCode) {
        this.imCode = imCode;
        return this;
    }

    public void setImCode(String imCode) {
        this.imCode = imCode;
    }

    public Set<Oferte> getOfertes() {
        return this.ofertes;
    }

    public Users ofertes(Set<Oferte> ofertes) {
        this.setOfertes(ofertes);
        return this;
    }

    public Users addOferte(Oferte oferte) {
        this.ofertes.add(oferte);
        oferte.setUsers(this);
        return this;
    }

    public Users removeOferte(Oferte oferte) {
        this.ofertes.remove(oferte);
        oferte.setUsers(null);
        return this;
    }

    public void setOfertes(Set<Oferte> ofertes) {
        if (this.ofertes != null) {
            this.ofertes.forEach(i -> i.setUsers(null));
        }
        if (ofertes != null) {
            ofertes.forEach(i -> i.setUsers(this));
        }
        this.ofertes = ofertes;
    }

    public Set<Polite> getPolites() {
        return this.polites;
    }

    public Users polites(Set<Polite> polites) {
        this.setPolites(polites);
        return this;
    }

    public Users addPolite(Polite polite) {
        this.polites.add(polite);
        polite.setUsers(this);
        return this;
    }

    public Users removePolite(Polite polite) {
        this.polites.remove(polite);
        polite.setUsers(null);
        return this;
    }

    public void setPolites(Set<Polite> polites) {
        if (this.polites != null) {
            this.polites.forEach(i -> i.setUsers(null));
        }
        if (polites != null) {
            polites.forEach(i -> i.setUsers(this));
        }
        this.polites = polites;
    }

    public Set<Tranzactii> getTranzactiis() {
        return this.tranzactiis;
    }

    public Users tranzactiis(Set<Tranzactii> tranzactiis) {
        this.setTranzactiis(tranzactiis);
        return this;
    }

    public Users addTranzactii(Tranzactii tranzactii) {
        this.tranzactiis.add(tranzactii);
        tranzactii.setUsers(this);
        return this;
    }

    public Users removeTranzactii(Tranzactii tranzactii) {
        this.tranzactiis.remove(tranzactii);
        tranzactii.setUsers(null);
        return this;
    }

    public void setTranzactiis(Set<Tranzactii> tranzactiis) {
        if (this.tranzactiis != null) {
            this.tranzactiis.forEach(i -> i.setUsers(null));
        }
        if (tranzactiis != null) {
            tranzactiis.forEach(i -> i.setUsers(this));
        }
        this.tranzactiis = tranzactiis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", cnpCuiClient='" + getCnpCuiClient() + "'" +
            ", numeAsigurat='" + getNumeAsigurat() + "'" +
            ", prenumeAsigurat='" + getPrenumeAsigurat() + "'" +
            ", tipAsigurat='" + getTipAsigurat() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", imCode='" + getImCode() + "'" +
            "}";
    }
}
