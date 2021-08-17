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

    @OneToMany(mappedBy = "u")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "u", "t" }, allowSetters = true)
    private Set<Oferte> os = new HashSet<>();

    @OneToMany(mappedBy = "u")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "u", "t" }, allowSetters = true)
    private Set<Polite> ps = new HashSet<>();

    @OneToMany(mappedBy = "u")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "o", "p", "u" }, allowSetters = true)
    private Set<Tranzactii> ts = new HashSet<>();

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

    public Set<Oferte> getOs() {
        return this.os;
    }

    public Users os(Set<Oferte> ofertes) {
        this.setOs(ofertes);
        return this;
    }

    public Users addO(Oferte oferte) {
        this.os.add(oferte);
        oferte.setU(this);
        return this;
    }

    public Users removeO(Oferte oferte) {
        this.os.remove(oferte);
        oferte.setU(null);
        return this;
    }

    public void setOs(Set<Oferte> ofertes) {
        if (this.os != null) {
            this.os.forEach(i -> i.setU(null));
        }
        if (ofertes != null) {
            ofertes.forEach(i -> i.setU(this));
        }
        this.os = ofertes;
    }

    public Set<Polite> getPs() {
        return this.ps;
    }

    public Users ps(Set<Polite> polites) {
        this.setPs(polites);
        return this;
    }

    public Users addP(Polite polite) {
        this.ps.add(polite);
        polite.setU(this);
        return this;
    }

    public Users removeP(Polite polite) {
        this.ps.remove(polite);
        polite.setU(null);
        return this;
    }

    public void setPs(Set<Polite> polites) {
        if (this.ps != null) {
            this.ps.forEach(i -> i.setU(null));
        }
        if (polites != null) {
            polites.forEach(i -> i.setU(this));
        }
        this.ps = polites;
    }

    public Set<Tranzactii> getTs() {
        return this.ts;
    }

    public Users ts(Set<Tranzactii> tranzactiis) {
        this.setTs(tranzactiis);
        return this;
    }

    public Users addT(Tranzactii tranzactii) {
        this.ts.add(tranzactii);
        tranzactii.setU(this);
        return this;
    }

    public Users removeT(Tranzactii tranzactii) {
        this.ts.remove(tranzactii);
        tranzactii.setU(null);
        return this;
    }

    public void setTs(Set<Tranzactii> tranzactiis) {
        if (this.ts != null) {
            this.ts.forEach(i -> i.setU(null));
        }
        if (tranzactiis != null) {
            tranzactiis.forEach(i -> i.setU(this));
        }
        this.ts = tranzactiis;
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
