package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.StareOferta;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Oferte} entity.
 */
public class OferteDTO implements Serializable {

    private Long id;

    private String codOferta;

    private Instant dataStartPolita;

    private Instant dataEnd;

    private StareOferta stareOferta;

    private String idProdus;

    private String valuta;

    private String sumaPlata;

    private String idAgent;

    private Integer idUser;

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

    public Instant getDataStartPolita() {
        return dataStartPolita;
    }

    public void setDataStartPolita(Instant dataStartPolita) {
        this.dataStartPolita = dataStartPolita;
    }

    public Instant getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Instant dataEnd) {
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
            ", sumaPlata='" + getSumaPlata() + "'" +
            ", idAgent='" + getIdAgent() + "'" +
            ", idUser=" + getIdUser() +
            ", users=" + getUsers() +
            "}";
    }
}
