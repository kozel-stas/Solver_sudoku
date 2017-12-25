package com.company;

public class Solver {
    private Table a;

    public Solver() {
        this.a = new Table();
    }

    public Solver(Table a) {
        this.a = a;
    }

    private void forecast_answer() {
        a.forecast_answer_cube99();
        a.forecast_answer_cube33();
        while (a.if_constants_is_exist()==true) {
            a.forecast_answer_cube99();
            a.forecast_answer_cube33();
        }
    }

    public void solve() {
        forecast_answer();
        if (a.Check_answer()==true){
            System.out.println("Ответ:");
            System.out.println(a);
            System.out.println("уровень сложности: низкий-нормальный");
        }
        else{
            a=getSolution(a);
            if (a.Check_answer()==true && a!=null){
                System.out.println("Ответ:");
                System.out.println(a);
                System.out.println("уровень сложности: сложно-очень сложно");
            }
            else {
                System.out.println("Ответ не найден, проверьте условие.");
            }
        }
    }

    private Table getSolution(Table tb){
        Table tab=new Table(tb);
        tab.forecast_answer_cube99();
        tab.forecast_answer_cube33();
        while (tab.if_constants_is_exist()==true) {
            tab.forecast_answer_cube99();
            tab.forecast_answer_cube33();
        }
        if(tab.Check_answer()==true) {
            return tab;
        }
        else {
            if (tab.all_cell_is_empty()==true) return null;
            else{
                Table copy=new Table(tab);
                Cell b=copy.min_len_cell();
                Cell cop=new Cell(b);
                for(int el:cop.getSet_of_number()){
                    b.make_const(el);
                    Table ans=getSolution(copy);
                    if(ans!=null) {
                        return ans;
                    }
                }
            }
        }
        return null;
    }

}
