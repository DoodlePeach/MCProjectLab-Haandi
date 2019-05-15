package com.example.beginneractivity;

public class Promos {
    private String imageSrc;
    private String promoDesc;
    private String promoCode;

    public Promos() {
    }

    public Promos(String imageSrc, String promoDesc, String promoCode) {
        this.imageSrc = imageSrc;
        this.promoDesc = promoDesc;
        this.promoCode = promoCode;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
