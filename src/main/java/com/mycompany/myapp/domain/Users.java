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

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "tranzactie" }, allowSetters = true)
    private Set<Oferte> ofertas = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "tranzactie" }, allowSetters = true)
    private Set<Polite> politas = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "oferta", "polita", "user" }, allowSetters = true)
    private Set<Tranzactii> tranzacties = new HashSet<>();

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

    public Set<Oferte> getOfertas() {
        return this.ofertas;
    }

    public Users ofertas(Set<Oferte> ofertes) {
        this.setOfertas(ofertes);
        return this;
    }

    public Users addOferta(Oferte oferte) {
        this.ofertas.add(oferte);
        oferte.setUser(this);
        return this;
    }

    public Users removeOferta(Oferte oferte) {
        this.ofertas.remove(oferte);
        oferte.setUser(null);
        return this;
    }

    public void setOfertas(Set<Oferte> ofertes) {
        if (this.ofertas != null) {
            this.ofertas.forEach(i -> i.setUser(null));
        }
        if (ofertes != null) {
            ofertes.forEach(i -> i.setUser(this));
        }
        this.ofertas = ofertes;
    }

    public Set<Polite> getPolitas() {
        return this.politas;
    }

    public Users politas(Set<Polite> polites) {
        this.setPolitas(polites);
        return this;
    }

    public Users addPolita(Polite polite) {
        this.politas.add(polite);
        polite.setUser(this);
        return this;
    }

    public Users removePolita(Polite polite) {
        this.politas.remove(polite);
        polite.setUser(null);
        return this;
    }

    public void setPolitas(Set<Polite> polites) {
        if (this.politas != null) {
            this.politas.forEach(i -> i.setUser(null));
        }
        if (polites != null) {
            polites.forEach(i -> i.setUser(this));
        }
        this.politas = polites;
    }

    public Set<Tranzactii> getTranzacties() {
        return this.tranzacties;
    }

    public Users tranzacties(Set<Tranzactii> tranzactiis) {
        this.setTranzacties(tranzactiis);
        return this;
    }

    public Users addTranzactie(Tranzactii tranzactii) {
        this.tranzacties.add(tranzactii);
        tranzactii.setUser(this);
        return this;
    }

    public Users removeTranzactie(Tranzactii tranzactii) {
        this.tranzacties.remove(tranzactii);
        tranzactii.setUser(null);
        return this;
    }

    public void setTranzacties(Set<Tranzactii> tranzactiis) {
        if (this.tranzacties != null) {
            this.tranzacties.forEach(i -> i.setUser(null));
        }
        if (tranzactiis != null) {
            tranzactiis.forEach(i -> i.setUser(this));
        }
        this.tranzacties = tranzactiis;
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
