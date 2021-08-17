package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Asigurat;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Users} entity.
 */
public class UsersDTO implements Serializable {

    private Long id;

    private String cnpCuiClient;

    private String numeAsigurat;

    private String prenumeAsigurat;

    private Asigurat tipAsigurat;

    private String telefon;

    private String email;

    private String imCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpCuiClient() {
        return cnpCuiClient;
    }

    public void setCnpCuiClient(String cnpCuiClient) {
        this.cnpCuiClient = cnpCuiClient;
    }

    public String getNumeAsigurat() {
        return numeAsigurat;
    }

    public void setNumeAsigurat(String numeAsigurat) {
        this.numeAsigurat = numeAsigurat;
    }

    public String getPrenumeAsigurat() {
        return prenumeAsigurat;
    }

    public void setPrenumeAsigurat(String prenumeAsigurat) {
        this.prenumeAsigurat = prenumeAsigurat;
    }

    public Asigurat getTipAsigurat() {
        return tipAsigurat;
    }

    public void setTipAsigurat(Asigurat tipAsigurat) {
        this.tipAsigurat = tipAsigurat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImCode() {
        return imCode;
    }

    public void setImCode(String imCode) {
        this.imCode = imCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersDTO)) {
            return false;
        }

        UsersDTO usersDTO = (UsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersDTO{" +
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
