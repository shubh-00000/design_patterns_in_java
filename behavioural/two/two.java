package behavioural.two;


import java.util.ArrayList;
import java.util.List;

class question{ //memento class
    private int i;
    
    public question(int i){
        this.i=i;
    }
    
    public int getQuestion(){
        return i;
    }
    
}

class assignment{ //originator class
    private int i;
    
    public void select(int question){
        this.i = i;
    }
    
    public int current(){
        return i;
    }
    
    public question solution(){
        return new question(i); //creates a new question with current index
    }
    
    public void display(question q){
        i = q.getQuestion(); //sets current question to the question stored in memento, to be printed
    }
}

class solver{ //caretaker class 
    private List<question> q1 = new ArrayList<question>(); //list of questions
    
    public void solve(question i){
        q1.add(i); //adds question to list
    }
    
    public question get(int index){
        return q1.get(index); //gets question at given index
    }
}

public class two {
    public static void main(String[] args) {
        
        assignment a1 = new assignment();
        solver s = new solver();
        
        a1.select(1); //selects question at index one
        a1.select(2); //selects question at index two
        s.solve(a1.solution()); //stores current question(2) in caretaker object- solver for question to be solved
        
        a1.select(3); //selects third question
        s.solve(a1.solution()); //stores current question(3) in caretaker object- solver for question to be solved
        
        a1.select(4); //selects fourth question
        System.out.print("The current question selected is: "+a1.current()+"\n"); //prints current question (4)
        
        a1.display(s.get(0)); //gets the first question to be added in caretaker object/ or to be solved (q2) and sets it as current
        System.out.print("The first question to be solved was: "+a1.current()+"\n"); //prints current question (2)
        
        a1.display(s.get(1)); //gets the second question to be added in caretaker object/ or to be solved (q3) and sets it as current
        System.out.print("The second question to be solved was: "+a1.current()+"\n  "); //prints current question (3)
   }
    
}
