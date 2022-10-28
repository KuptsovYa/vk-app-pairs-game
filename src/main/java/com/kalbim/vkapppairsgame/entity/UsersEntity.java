package com.kalbim.vkapppairsgame.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "promodb", catalog = "")
public class UsersEntity {
    private int user;
    private int coins;
    private Integer gameCount;
    private Integer notifications;
    private String circs;

    @Id
    @Column(name = "user")
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Basic
    @Column(name = "coins")
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Basic
    @Column(name = "gameCount")
    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    @Basic
    @Column(name = "notifications")
    public Integer getNotifications() {
        return notifications;
    }

    public void setNotifications(Integer notifications) {
        this.notifications = notifications;
    }
    @Basic
    @Column(name = "circs")
    public String getCircs() {
        return circs;
    }

    public void setCircs(String circs) {
        this.circs = circs;
    }
}
