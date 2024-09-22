package structural.six;
//FACADE DESIGN PATTERN
interface assignment1 {
    void solve();
} 

class question1 implements assignment1 {
    @Override
    public void solve() {
        System.out.print("question one was solved. \n");
    }
}
class question2 implements assignment1 {
    @Override   
    public void solve() {
        System.out.print("question two was solved. \n");
    }
}
class question3 implements assignment1 {
    @Override
    public void solve() {
        System.out.print("question three was solved. \n");
    }
}
//interface with methods to call
class solver {
    private assignment1 question1;
    private assignment1 question2;
    private assignment1 question3;
    
    public solver() {
        question1 = new question1();
        question2 = new question2();
        question3 = new question3();
    }
    public void solve1(){
        question1.solve();
    }
    public void solve2(){
        question2.solve();
    }
    public void solve3(){
        question3.solve();
    }
}
//facade class calling methods

public class six {
    public static void main(String[] args) {
        solver obj = new solver();
        obj.solve1();
        obj.solve2();
        obj.solve3();
    }
}