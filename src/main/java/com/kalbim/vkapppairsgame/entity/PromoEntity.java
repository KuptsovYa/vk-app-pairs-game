package com.kalbim.vkapppairsgame.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "promo", schema = "promodb", catalog = "")
public class PromoEntity {
    private int idpromo;
    private String promo;
    private int price;
    private int used;

    @Id
    @Column(name = "idpromo")
    public int getIdpromo() {
        return idpromo;
    }

    public void setIdpromo(int idpromo) {
        this.idpromo = idpromo;
    }

    @Basic
    @Column(name = "promo")
    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "used")
    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoEntity that = (PromoEntity) o;
        return idpromo == that.idpromo &&
                price == that.price &&
                used == that.used &&
                Objects.equals(promo, that.promo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpromo, promo, price, used);
    }
}
