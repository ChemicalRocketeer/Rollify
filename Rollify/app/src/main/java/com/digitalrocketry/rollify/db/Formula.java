package com.digitalrocketry.rollify.db;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Comparator;

/**
 * The database model for a Formula
 */
public class Formula extends SugarRecord<Formula> {

    private String name;
    private String expression;
    private long creationTime;
    private int uses;

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
        this.creationTime = System.currentTimeMillis();
        this.uses = 0;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public static final Comparator<Formula> COMPARE_BY_NAME = new Comparator<Formula>() {
        public int compare(Formula a, Formula b) {
            return a.name.compareTo(b.name);
        }
    };

    public static final Comparator<Formula> COMPARE_BY_USE_RATE = new Comparator<Formula>() {
        public int compare(Formula a, Formula b) {
            return Float.compare(a.getUseRate(), b.getUseRate());
        }
    };

    public static final Comparator<Formula> COMPARE_BY_CREATE_TIME = new Comparator<Formula>() {
        public int compare(Formula a, Formula b) {
            return Long.compare(a.creationTime, b.creationTime);
        }
    };

    public static Formula findByID(long id) {
        return Select.from(Formula.class).where(Condition.prop("id").eq(id)).first();
    }

    public static Formula findByName(String name) {
        return Select.from(Formula.class).where(Condition.prop("name").eq(name)).first();
    }
}
