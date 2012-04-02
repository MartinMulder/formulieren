package nl.voorbeeldsite.formulieren.global;

import javax.faces.model.SelectItem;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Persoon {
    @Size(max=3)
    private String voornaam;
    private String achternaam;
    private Date geboortedatum;

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public List<SelectItem> getGeslachtSelectItems() {
        List<SelectItem> geslachtList = new ArrayList<SelectItem>();
        geslachtList.add(new SelectItem("Man"));
        geslachtList.add(new SelectItem("Vrouw"));
        return geslachtList;
    }
}
