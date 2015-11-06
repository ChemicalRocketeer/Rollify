package com.digitalrocketry.rollify.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.concurrent.TimeUnit;

/**
 * The database model for a Formula
 */
@Table(name = "Formulas")
public class Formula extends Model {

    @Column(name = "ID")
    public long id;

    @Column(name = "Name")
    public String name;

    @Column(name = "Expression")
    public String expression;

    @Column(name = "CreationTime")
    public long creationTime;

    @Column(name = "Uses")
    public int uses;

    public Formula(String name, String expression, long creationTime, int uses) {
        super();
        this.name = name;
        this.expression = expression;
        this.creationTime = creationTime;
        this.uses = uses;
    }

    public Formula(String name, String expression) {
        this(name, expression, System.currentTimeMillis(), 0);
    }

    public Formula() {
        super();
    }

    private static final long MILLIS_PER_HOUR = 1000 * 60 * 60;

    /**
     * @return the use rate per hour
     */
    public float getUseRate() {
        // hours this formula has existed
        long time = (System.currentTimeMillis() - creationTime) / MILLIS_PER_HOUR;
        if (time == 0) time = 1; // prevent any potential division by 0.
        return uses / time;
    }
}
