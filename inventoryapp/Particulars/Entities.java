package com.example.abhi.inventoryapp.Particulars;

public class Entities {

    private final String iArticleName;
    private final String iArticlePrice;
    private final int iArticleQuantity;
    private final String iSellerName;
    private final String iSellerPhoneNumber;
    private final String iSellerEmail;
    private final String iArticleImage;

    public Entities(String articlename, String articleprice, int articlequantity, String sellername, String sellerphonenumber, String selleremail, String articleimage) {
        iArticleName = articlename;
        iArticlePrice = articleprice;
        iArticleQuantity = articlequantity;
        iSellerName = sellername;
        iSellerPhoneNumber = sellerphonenumber;
        iSellerEmail = selleremail;
        iArticleImage = articleimage;
    }

    public String getArticleName() {
        return iArticleName;
    }

    public String getArticlePrice() {
        return iArticlePrice;
    }

    public int getArticleQuantity() {
        return iArticleQuantity;
    }

    public String getSellerName() {
        return iSellerName;
    }

    public String getSellerPhoneNumber() {
        return iSellerPhoneNumber;
    }

    public String getSellerEmail() {
        return iSellerEmail;
    }

    public String getArticleImage() {
        return iArticleImage;
    }
}
