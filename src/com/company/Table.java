package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Table {
    private Cell [][]Tab= new Cell[9][9];

    public Table(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("sudoku.txt"));
            String line;
            int i=0,j=0;
            while ((line = reader.readLine()) != null) {
                for (int s=0;s<17;s+=2){
                    if (line.charAt(s)=='-'){
                        Cell a=new Cell();
                        this.Tab[i][j]=a;
                        //System.out.println(a);
                    }
                    else {
                        Cell a=new Cell(line.charAt(s)-48);
                        //System.out.println(a);
                        this.Tab[i][j]=a;
                    }
                    j++;
                }
                j=0;
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Table(Table copy){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++){
                Cell b=new Cell(copy.getTab()[i][j]);
                Tab[i][j]=b;
            }
    }

    public Cell[][] getTab() {
        return Tab;
    }

    public void delnumberonSet(int line,int column,int number){
        for (int i=0;i<9;i++){
            if (this.Tab[line][i].getSet_of_number()!=null)
                this.Tab[line][i].delnumber(number);
            if (this.Tab[i][column].getSet_of_number()!=null)
                this.Tab[i][column].delnumber(number);
        }
    }

    @Override
    public String toString() {
        StringBuilder a=new StringBuilder();
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                a.append(Tab[i][j].toString());
            }
            a.append('\n');
        }
        return a.toString();
    }

    protected void forecast_answer_cube99() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (Tab[i][j].getValue() != 0)
                    delnumberonSet(i, j, Tab[i][j].getValue());
    }

    private void delete_number_on_cube33(int number, int cube1, int cube2) {
        for (int i = cube1; i < cube1 + 3; i++)
            for (int j = cube2; j < cube2 + 3; j++) {
                if (Tab[i][j].getSet_of_number() != null && Tab[i][j].getSet_of_number().contains(number) == true)
                    Tab[i][j].delnumber(number);
            }
    }

    private boolean if_constants_is_exist_in_cube33(int cube1, int cube2) {
        boolean ans = false;
        for (int i = cube1; i < cube1 + 3; i++)
            for (int j = cube2; j < cube2 + 3; j++) {
                if (Tab[i][j].getSet_of_number() != null) {
                    for (int list : Tab[i][j].getSet_of_number()) {
                        int unique = 0;
                        for (int i1 = cube1; i1 < cube1 + 3; i1++)
                            for (int j1 = cube2; j1 < cube2 + 3; j1++) {
                                if (i == i1 && j == j1) {
                                    unique++;
                                } else {
                                    if (Tab[i1][j1].getSet_of_number() != null) {
                                        if (Tab[i1][j1].getSet_of_number().contains(list) == false)
                                            unique++;
                                    } else {
                                        unique++;
                                    }
                                }
                            }
                        if (unique == 9) {
                            ans = true;
                            Tab[i][j].make_const(list);
                            break;
                        }
                    }
                }
            }
        return ans;
    }

    protected boolean if_constants_is_exist(){
        boolean ans=false;
//        for (int i=0;i<9;i++){
//            if (if_constants_is_exist_in_line(i)==true || if_constants_is_exist_in_column(i)==true)
//                ans=true;
//        }
        for (int cube1 = 0; cube1 < 9; cube1 += 3)
            for (int cube2 = 0; cube2 < 9; cube2 += 3)
                if (if_constants_is_exist_in_cube33(cube1,cube2)==true)
                    ans=true;
        return ans;
    }

    protected void forecast_answer_cube33() {
        for (int cube1 = 0; cube1 < 9; cube1 += 3)
            for (int cube2 = 0; cube2 < 9; cube2 += 3) {
                for (int i = cube1; i < cube1 + 3; i++)
                    for (int j = cube2; j < cube2 + 3; j++) {
                        if (Tab[i][j].getSet_of_number() == null)
                            delete_number_on_cube33(Tab[i][j].getValue(), cube1, cube2);
                    }
            }
    }

    private boolean unique_in_cube(int line, int column) {
        for (int cube1 = 0; cube1 < 9; cube1 += 3)
            for (int cube2 = 0; cube2 < 9; cube2 += 3) {
                if (line >= cube1 && line < cube1 + 3 && column >= cube2 && column < cube2 + 3) {
                    ArrayList<Integer> arr = new ArrayList<>(8);
                    for (int i = cube1; i < cube1 + 3; i++)
                        for (int j = cube2; j < cube2 + 3; j++) {
                            if (i == line && j == column) {
                            } else {
                                arr.add(Tab[i][j].getValue());
                            }
                        }
                    if (arr.contains(Tab[line][column].getValue()) == false && arr.contains(0)==false)
                        return true;
                }
            }
        return false;
    }

    private boolean unique_in_line(int line, int column) {
        ArrayList<Integer> arr = new ArrayList<>(8);
        for (int i = 0; i < 9; i++) {
            if (i != column) {
                arr.add(Tab[line][i].getValue());
            }
        }
        if (arr.contains(Tab[line][column].getValue()) == false && arr.contains(0)==false)
            return true;
        return false;
    }

    private boolean unique_in_column(int line, int column) {
        ArrayList<Integer> arr = new ArrayList<>(8);
        for (int i = 0; i < 9; i++) {
            if (i != line) {
                arr.add(Tab[i][column].getValue());
            }
        }
        if (arr.contains(Tab[line][column].getValue()) == false && arr.contains(0)==false)
            return true;
        return false;
    }

    protected boolean Check_answer() {
        boolean answer = true;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if (unique_in_column(i, j) == false || unique_in_cube(i, j) == false || unique_in_line(i, j) == false)
                    answer = false;
            }
        return answer;
    }

    protected boolean all_cell_is_empty(){
        int cell=0;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (Tab[i][j].getSet_of_number()==null ||Tab[i][j].getSet_of_number().size()==0)
                    cell++;
        if(cell==81) return true;
        else return false;
    }

    protected Cell min_len_cell(){
        int min=10;
        Cell ans=null;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++){
                if (Tab[i][j].getSet_of_number()!=null){
                    if (Tab[i][j].getSet_of_number().size()<min){
                        min=Tab[i][j].getSet_of_number().size();
                        ans=Tab[i][j];
                    }
                }
            }
        return ans;
    }

    public void setTab(Cell[][] tab) {
        Tab = tab;
    }
}
