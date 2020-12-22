package com.example.rakbuku.rakbuku;
import com.google.firebase.firestore.Exclude;

public class Note {
    private String documentId;
    private String Nama;
    private String Email;
    private String UrlImage;
    private String Lokasi;

    public Note() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note(String Nama, String Email, String UrlImage, String Lokasi) {
        this.Nama = Nama;
        this.Email = Email;
        this.UrlImage = UrlImage;
        this.Lokasi = Lokasi;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public String getNama() {
        return Nama;
    }

    public String getEmail() {
        return Email;
    }
}
