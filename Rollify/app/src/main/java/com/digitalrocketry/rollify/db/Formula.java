package com.digitalrocketry.rollify.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * The database model for a Formula
 */
@Table(name = "Formulas")
public class Formula extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Expression")
    public String expression;

    @Column(name = "CreationTime")
    public long creationTime;

    @Column(name = "Uses")
    public int uses;

    public Formula(String name, String expression) {
        this(name, expression, System.currentTimeMillis(), 0);
    }

    public Formula(String name, String expression, long creationTime, int uses) {
        super();
        this.name = name;
        this.expression = expression;
        this.creationTime = creationTime;
        this.uses = uses;
    }

    public Formula() {
        super();
    }
}
