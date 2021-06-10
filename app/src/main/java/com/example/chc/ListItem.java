package com.example.chc;

public class ListItem {
    private String id;
    private String adresa, auto, brtel, brtel2, datum, email, grad, ime, number, posted_at, prezime, radiona, status, tekst, vrijeme, year,zip, url, prihvacennovitermin;

    // Constructor
    public ListItem(String adresa, String auto, String brtel, String brtel2, String datum, String email, String grad, String ime, String number, String posted_at, String prezime, String radiona, String status, String tekst, String vrijeme, String year, String zip, String url, String prihvacennovitermin, String id) {
        this.adresa=adresa;
        this.auto=auto;
        this.brtel=brtel;
        this.brtel2=brtel2;
        this.datum=datum;
        this.email=email;
        this.grad=grad;
        this.ime=ime;
        this.number=number;
        this.posted_at=posted_at;
        this.prezime=prezime;
        this.radiona=radiona;
        this.status=status;
        this.tekst=tekst;
        this.vrijeme=vrijeme;
        this.year=year;
        this.zip=zip;
        this.url=url;
        this.id=id;
        this.prihvacennovitermin=prihvacennovitermin;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getAuto() {
        return auto;
    }

    public String getBrtel() {
        return brtel;
    }

    public String getBrtel2() {
        return brtel2;
    }

    public String getDatum() {
        return datum;
    }

    public String getEmail() {
        return email;
    }

    public String getGrad() {
        return grad;
    }

    public String getIme() {
        return ime;
    }

    public String getNumber() {
        return number;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getRadiona() {
        return radiona;
    }

    public String getStatus() {
        return status;
    }

    public String getTekst() {
        return tekst;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getYear() {
        return year;
    }

    public String getZip() {
        return zip;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getPrihvacennovitermin() {
        return prihvacennovitermin;
    }
}
