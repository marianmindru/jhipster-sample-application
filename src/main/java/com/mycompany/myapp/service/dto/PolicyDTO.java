package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Policy} entity.
 */
public class PolicyDTO implements Serializable {

    private Long id;

    @NotNull
    private String numarPolita;

    @NotNull
    private String seriePolita;

    @NotNull
    private String codOferta;

    private LocalDate dataStart;

    private LocalDate dataEnd;

    private Integer perioada;

    @Size(max = 10)
    private String idProdus;

    @Size(max = 10)
    private String valuta;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.9999")
    private Double sumaPlata;

    private String idAgent;

    private OfferDTO offer;

    private ClientDTO client;

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

    public LocalDate getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(LocalDate dataEnd) {
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

    public OfferDTO getOffer() {
        return offer;
    }

    public void setOffer(OfferDTO offer) {
        this.offer = offer;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyDTO)) {
            return false;
        }

        PolicyDTO policyDTO = (PolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, policyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyDTO{" +
            "id=" + getId() +
            ", numarPolita='" + getNumarPolita() + "'" +
            ", seriePolita='" + getSeriePolita() + "'" +
            ", codOferta='" + getCodOferta() + "'" +
            ", dataStart='" + getDataStart() + "'" +
            ", dataEnd='" + getDataEnd() + "'" +
            ", perioada=" + getPerioada() +
            ", idProdus='" + getIdProdus() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", sumaPlata=" + getSumaPlata() +
            ", idAgent='" + getIdAgent() + "'" +
            ", offer=" + getOffer() +
            ", client=" + getClient() +
            "}";
    }
}
