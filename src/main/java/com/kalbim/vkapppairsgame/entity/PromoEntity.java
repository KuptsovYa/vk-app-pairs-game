package com.kalbim.vkapppairsgame.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
