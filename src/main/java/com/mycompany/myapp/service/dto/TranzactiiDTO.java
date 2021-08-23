package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.StatusPlata;
import java.io.Serializable;
import java.time.LocalDate;
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

    private LocalDate dataPlata;

    private String valuta;

    private String referintaIng;

    private OferteDTO oferta;

    private ClientsDTO user;

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

    public LocalDate getDataPlata() {
        return dataPlata;
    }

    public void setDataPlata(LocalDate dataPlata) {
        this.dataPlata = dataPlata;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getReferintaIng() {
        return referintaIng;
    }

    public void setReferintaIng(String referintaIng) {
        this.referintaIng = referintaIng;
    }

    public OferteDTO getOferta() {
        return oferta;
    }

    public void setOferta(OferteDTO oferta) {
        this.oferta = oferta;
    }

    public ClientsDTO getUser() {
        return user;
    }

    public void setUser(ClientsDTO user) {
        this.user = user;
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
            ", referintaIng='" + getReferintaIng() + "'" +
            ", oferta=" + getOferta() +
            ", user=" + getUser() +
            "}";
    }
}
