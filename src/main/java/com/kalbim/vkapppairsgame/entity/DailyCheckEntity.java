package com.kalbim.vkapppairsgame.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dailyCheck", schema = "promodb", catalog = "")
public class DailyCheckEntity {

    private int user;

    private Timestamp lastLoginDate;

    private int dayCount;

    @Id
    @Column(name = "user")
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Basic
    @Column(name = "lastLoginDate")
    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Basic
    @Column(name = "dayCount")
    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }
}
