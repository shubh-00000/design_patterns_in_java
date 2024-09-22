package structural.five;
//PROXY DESIGN PATTERN

import java.util.ArrayList;
import java.util.List;

//interface with 'display' method
interface assignment
{
    public void display(int i) throws Exception;//every assignment has a display method
} 
//concrete assignment type: questions.
class question implements assignment
{
    @Override
    public void display(int i)
    {
        //Solve the Question
        System.out.println("Question "+i+" is solved.");
    }
}

//proxy class
class proxy_question implements assignment{
    private assignment q = new question(); 
    //instances 'question' class to handle 'display' method
    private static final List<Integer> no;
    //list of question numbers currently not required.
    static
    {
        no = new ArrayList<>();
        no.add(2);
        no.add(3);
        no.add(5);
        no.add(7);
        }
    
    @Override
    //if question number is in list, exception is called, otherwise 'display' method is called.
    public void display(int i) throws Exception{
        if(no.contains(i)){
            throw new Exception ("Question "+i+ " out of current requirement. \n");
        }
        q.display(i);
    }
}
public class five {
    public static void main(String[] args) {
        assignment q = new proxy_question();
        //proxy_question instanced to call 'display' method
        for (int i=1; i<8; i++){
        try{
            q.display(i);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        }
        //a for loop for questions 1 to 7.
    }
}

