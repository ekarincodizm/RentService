/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.model.test;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Sumrit Pratumjit
 */
public class Ws implements Serializable {

    private BigInteger id;
    private Integer levelGen;
    private String positionName;
    private BigInteger uplineId;
    private Integer align;
    private BigInteger totalMember;
    private Double organizationPv;
    private Double weak;
    private Double strong;
    private Double wkPcent;
    private Double stPcent;
    private Double maxBonus;
    private Double weakBonus;
    private Double strongBonus;
    private Double bonus;
    private Integer circle;
    
    /// ฺbean for WS Balance Bonus 
    private Integer maxUnit;
    private Integer unit;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getMaxUnit() {
        return maxUnit;
    }

    public void setMaxUnit(Integer maxUnit) {
        this.maxUnit = maxUnit;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getLevelGen() {
        return levelGen;
    }

    public void setLevelGen(Integer levelGen) {
        this.levelGen = levelGen;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public BigInteger getUplineId() {
        return uplineId;
    }

    public void setUplineId(BigInteger uplineId) {
        this.uplineId = uplineId;
    }

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public BigInteger getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(BigInteger totalMember) {
        this.totalMember = totalMember;
    }

    public Double getOrganizationPv() {
        return organizationPv;
    }

    public void setOrganizationPv(Double organizationPv) {
        this.organizationPv = organizationPv;
    }

    public Double getWeak() {
        return weak;
    }

    public void setWeak(Double weak) {
        this.weak = weak;
    }

    public Double getStrong() {
        return strong;
    }

    public void setStrong(Double strong) {
        this.strong = strong;
    }

    public Double getWkPcent() {
        return wkPcent;
    }

    public void setWkPcent(Double wkPcent) {
        this.wkPcent = wkPcent;
    }

    public Double getStPcent() {
        return stPcent;
    }

    public void setStPcent(Double stPcent) {
        this.stPcent = stPcent;
    }

    public Double getMaxBonus() {
        return maxBonus;
    }

    public void setMaxBonus(Double maxBonus) {
        this.maxBonus = maxBonus;
    }

    public Double getWeakBonus() {
        return weakBonus;
    }

    public void setWeakBonus(Double weakBonus) {
        this.weakBonus = weakBonus;
    }

    public Double getStrongBonus() {
        return strongBonus;
    }

    public void setStrongBonus(Double strongBonus) {
        this.strongBonus = strongBonus;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Integer getCircle() {
        return circle;
    }

    public void setCircle(Integer circle) {
        this.circle = circle;
    }

}
