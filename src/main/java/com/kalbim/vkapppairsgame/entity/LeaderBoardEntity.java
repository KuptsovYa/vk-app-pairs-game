package com.kalbim.vkapppairsgame.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leaderBoard", schema = "promodb", catalog = "")
public class LeaderBoardEntity {

    private int userId;
    private int coins;

    @Id
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "coins")
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
