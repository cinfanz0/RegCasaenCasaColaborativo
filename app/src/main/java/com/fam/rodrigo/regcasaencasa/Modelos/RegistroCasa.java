package com.fam.rodrigo.regcasaencasa.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rodrigo on 28/01/2016.
 */
public class RegistroCasa implements Parcelable {
    private String idreg;
    private String calle;
    private String ncasa;
    private String amocasa;
    private String territorio;
    private String cfecha;

    public RegistroCasa() {
    }

    protected RegistroCasa(Parcel in) {
        calle = in.readString();
        ncasa = in.readString();
        amocasa = in.readString();
        territorio = in.readString();
        cfecha = in.readString();
    }

    public static final Creator<RegistroCasa> CREATOR = new Creator<RegistroCasa>() {
        @Override
        public RegistroCasa createFromParcel(Parcel in) {
            return new RegistroCasa(in);
        }

        @Override
        public RegistroCasa[] newArray(int size) {
            return new RegistroCasa[size];
        }
    };

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNcasa() {
        return ncasa;
    }

    public void setNcasa(String ncasa) {
        this.ncasa = ncasa;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getCfecha() {
        return cfecha;
    }

    public void setCfecha(String cfecha) {
        this.cfecha = cfecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(calle);
        dest.writeString(ncasa);
        dest.writeString(amocasa);
        dest.writeString(territorio);
        dest.writeString(cfecha);
    }

    public String getAmocasa() {
        return amocasa;
    }

    public void setAmocasa(String amocasa) {
        this.amocasa = amocasa;
    }

    public String getIdreg() {
        return idreg;
    }

    public void setIdreg(String idreg) {
        this.idreg = idreg;
    }
}
