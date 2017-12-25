package com.company;

import java.util.ArrayList;

public class Cell {
    private int value;
    private ArrayList<Integer> set_of_number=new ArrayList<>(8);

    public Cell (){
        for (int i=1;i<10;i++)
            set_of_number.add(i);
    }

    public Cell (Cell copy){
        this.value=copy.getValue();
        if(copy.getSet_of_number()==null) this.set_of_number=null;
        else {
            for (int el : copy.set_of_number) {
                this.set_of_number.add(el);
            }
        }
    }

    public Cell (int value){
        this.value=value;
        this.set_of_number=null;
    }

    public ArrayList<Integer> getSet_of_number() {
        return set_of_number;
    }

    public int getValue() {
        return value;
    }

    public void setSet_of_number(ArrayList<Integer> set_of_number) {
        this.set_of_number = set_of_number;
    }

    public void setValue(int value) {
        if (value<10 && value>0)
            this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.value == ((Cell) obj).getValue() && this.set_of_number.equals(((Cell)obj).getSet_of_number()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder a=new StringBuilder("");
        a.append(this.value);
        a.append(" ");
        return a.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void delnumber(int number){
        if(this.set_of_number!=null)
        this.set_of_number.remove((Object) number);
    }

    public void make_const (int number) {
        this.value=number;
        this.set_of_number=null;
    }

}
