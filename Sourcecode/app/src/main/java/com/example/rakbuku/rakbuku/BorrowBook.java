package com.example.rakbuku.rakbuku;

public class BorrowBook {

    private String Judul;
    private String Email;
    private String UrlBook;
    private String Penulis;
    private String Penerbit;
    private String Harga;
    private String Tipe;
    private String idFotoBuku;

    public String getIdFotoBuku() {
        return idFotoBuku;
    }

    public void setIdFotoBuku(String idFotoBuku) {
        this.idFotoBuku = idFotoBuku;
    }

    public BorrowBook(String Judul, String Email, String UrlBook, String Harga, String Tipe, String Penulis, String Penerbit) {
        this.Judul = Judul;
        this.Email = Email;
        this.UrlBook = UrlBook;
        this.Harga = Harga;
        this.Tipe = Tipe;
        this.Penulis = Penulis;
        this.Penerbit = Penerbit;



    }

    public String getPenulis() {
        return Penulis;
    }

    public String getPenerbit() {
        return Penerbit;
    }

    public String getTipe() {
        return Tipe;
    }

    public String getHarga() {
        return Harga;
    }

    public String getJudul() {
        return Judul;
    }

    public String getEmail() {
        return Email;
    }

    public String getUrlBook() {
        return UrlBook;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUrlBook(String urlBook) {
        UrlBook = urlBook;
    }

    public void setPenulis(String penulis) {
        Penulis = penulis;
    }

    public void setPenerbit(String penerbit) {
        Penerbit = penerbit;
    }

    public void setTipe(String tipe) {
        Tipe = tipe;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }
}
