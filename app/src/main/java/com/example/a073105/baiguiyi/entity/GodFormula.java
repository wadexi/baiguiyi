package com.example.a073105.baiguiyi.entity;

import com.example.a073105.baiguiyi.db.ColumnName;
import com.example.a073105.baiguiyi.db.FieldIgnore;

public class GodFormula {

    @FieldIgnore()
    private int id ;
    private String name;
    private String imgPath;
    @ColumnName("attackNum")
    private long attackNumber;
    private long lifeNum;
    private long defenseNum;
    private float crit;
    private float critHurt;
    private float hit;
    private float resistance;
    private TestEntity testEntity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAttackNum() {
        return attackNumber;
    }

    public void setAttackNum(long attackNum) {
        this.attackNumber = attackNum;
    }

    public long getLifeNum() {
        return lifeNum;
    }

    public void setLifeNum(long lifeNum) {
        this.lifeNum = lifeNum;
    }

    public long getDefenseNum() {
        return defenseNum;
    }

    public void setDefenseNum(long defenseNum) {
        this.defenseNum = defenseNum;
    }

    public float getCrit() {
        return crit;
    }

    public void setCrit(float crit) {
        this.crit = crit;
    }

    public float getCritHurt() {
        return critHurt;
    }

    public void setCritHurt(float critHurt) {
        this.critHurt = critHurt;
    }

    public float getHit() {
        return hit;
    }

    public void setHit(float hit) {
        this.hit = hit;
    }

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgPath;
    }

    public void setImgpath(String imgpath) {
        this.imgPath = imgpath;
    }

    public long getAttackNumber() {
        return attackNumber;
    }

    public void setAttackNumber(long attackNumber) {
        this.attackNumber = attackNumber;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    @Override
    public String toString() {
        return "GodFormula{" +
                "id=" + id +
                ", name=" + name +
                ", imgpath='" + imgPath + '\'' +
                ", attackNumber=" + attackNumber +
                ", lifeNum=" + lifeNum +
                ", defenseNum=" + defenseNum +
                ", crit=" + crit +
                ", critHurt=" + critHurt +
                ", hit=" + hit +
                ", resistance=" + resistance +
                ", testEntity=" + testEntity +
                '}';
    }
}
