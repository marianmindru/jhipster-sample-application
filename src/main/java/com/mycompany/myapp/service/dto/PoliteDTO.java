package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Polite} entity.
 */
public class PoliteDTO implements Serializable {

    private Long id;

    private String numarPolita;

    private String seriePolita;

    private String codOferta;

    private Instant dataStart;

    private Instant dataEnd;

    private Integer perioada;

    private String idProdus;

    private String valuta;

    private String sumaPlata;

    private String idAgent;

    private Long idUser;

    private UsersDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCodOferta() {
        return codOferta;
    }

    public void setCodOferta(String codOferta) {
        this.codOferta = codOferta;
    }

    public Instant getDataStart() {
        return dataStart;
    }

    public void setDataStart(Instant dataStart) {
        this.dataStart = dataStart;
    }

    public Instant getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Instant dataEnd) {
        this.dataEnd = dataEnd;
    }

    public Integer getPerioada() {
        return perioada;
    }

    public void setPerioada(Integer perioada) {
        this.perioada = perioada;
    }

    public String getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoliteDTO)) {
            return false;
        }

        PoliteDTO politeDTO = (PoliteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, politeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoliteDTO{" +
            "id=" + getId() +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStart='" + getDataStart() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", perioada=" + getPerioada() +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata='" + getSumaPlata() + "'" +
            ", idAgent='" + getIdAgent() + "'" +
            ", idUser=" + getIdUser() +
            ", user=" + getUser() +
            "}";
    }
}
