package com.kalbim.vkapppairsgame.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "promodb", catalog = "")
public class UsersEntity {
    private int user;
    private int coins;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return user == that.user &&
                coins == that.coins;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, coins);
    }
}
