package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.StareOferta;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Oferte} entity.
 */
public class OferteDTO implements Serializable {

    private Long id;

    @NotNull
    private String codOferta;

    private LocalDate dataStartPolita;

    private LocalDate dataEnd;

    private StareOferta stareOferta;

    private String idProdus;

    private String valuta;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    private Double sumaPlata;

    private String idAgent;

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

    public LocalDate getDataStartPolita() {
        return dataStartPolita;
    }

    public void setDataStartPolita(LocalDate dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
    }

    public LocalDate getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public StareOferta getStareOferta() {
        return stareOferta;
    }

    public void setStareOferta(StareOferta stareOferta) {
        this.stareOferta = stareOferta;
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

    public Double getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(Double sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
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
        if (!(o instanceof OferteDTO)) {
            return false;
        }

        OferteDTO oferteDTO = (OferteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oferteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OferteDTO{" +
            "id=" + getId() +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStartPolita='" + getDataStartPolita() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", stareOferta='" + getStareOferta() + "'" +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata=" + getSumaPlata() +
            ", idAgent='" + getIdAgent() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
