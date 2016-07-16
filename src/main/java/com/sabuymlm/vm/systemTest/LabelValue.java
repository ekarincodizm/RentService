/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.vm.systemTest;

/**
 *
 * @author Sumrit Pratumjit
 */
public class LabelValue {
    private String label ;
    private Number value ;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public LabelValue(String label, Number value) {
        this.label = label;
        this.value = value;
    }
    
    public LabelValue() { 
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.label != null ? this.label.hashCode() : 0);
        hash = 41 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LabelValue other = (LabelValue) obj;
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LabelValue{" + "label=" + label + ", value=" + value + '}';
    }
    
}
