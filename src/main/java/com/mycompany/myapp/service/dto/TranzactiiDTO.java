package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.StatusPlata;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Tranzactii} entity.
 */
public class TranzactiiDTO implements Serializable {

    private Long id;

    private String codOferta;

    private String numarPolita;

    private String seriePolita;

    private String sumaPlatita;

    private StatusPlata statusPlata;

    private Instant dataPlata;

    private String valuta;

    private Integer idUser;

    private String referintaIng;

    private OferteDTO oferte;

    private PoliteDTO polite;

    private UsersDTO users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodOferta() {
        return codOferta;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public String getNumarPolita() {
        return numarPolita;
    }

    public void setNumarPolita(String numarPolita) {
        this.numarPolita = numarPolita;
    }

    public String getSeriePolita() {
        return seriePolita;
    }

    public void setSeriePolita(String seriePolita) {
        this.seriePolita = seriePolita;
    }

    public String getSumaPlatita() {
        return sumaPlatita;
    }

    public void setSumaPlatita(String sumaPlatita) {
        this.sumaPlatita = sumaPlatita;
    }

    public StatusPlata getStatusPlata() {
        return statusPlata;
    }

    public void setStatusPlata(StatusPlata statusPlata) {
        this.statusPlata = statusPlata;
    }

    public Instant getDataPlata() {
        return dataPlata;
    }

    public void setDataPlata(Instant dataPlata) {
        this.dataPlata = dataPlata;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getReferintaIng() {
        return referintaIng;
    }

    public void setReferintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
    }

    public OferteDTO getOferte() {
        return oferte;
    }

    public void setOferte(OferteDTO oferte) {
        this.oferte = oferte;
    }

    public PoliteDTO getPolite() {
        return polite;
    }

    public void setPolite(PoliteDTO polite) {
        this.polite = polite;
    }

    public UsersDTO getUsers() {
        return users;
    }

    public void setUsers(UsersDTO users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranzactiiDTO)) {
            return false;
        }

        TranzactiiDTO tranzactiiDTO = (TranzactiiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tranzactiiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranzactiiDTO{" +
            "id=" + getId() +
            ", codOferta='" + getCodOferta() + "'" +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", sumaPlatita='" + getSumaPlatita() + "'" +
            ", statusPlata='" + getStatusPlata() + "'" +
            ", dataPlata='" + getDataPlata() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", idUser=" + getIdUser() +
            ", referintaIng='" + getReferintaIng() + "'" +
            ", oferte=" + getOferte() +
            ", polite=" + getPolite() +
            ", users=" + getUsers() +
            "}";
    }
}
