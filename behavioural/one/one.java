package behavioural.one;

interface assignment
{
    public void display(context x);//every assignment has a display method
} 

class context{
    private assignment current; //object of interface assignment to set type and call method
    public context()
    {
        current = new question(); //setting initial type as question
    }
    
    public void setType (assignment type)
    {
        current = type; //setType method to change
    }
    
    public void display() { 
        current.display(this); //calling display method of assignment method
        }
}

//concrete assignment type: questions.
class question implements assignment
{
    @Override
    public void display(context x)
    {
        //Solve the Question
        System.out.println("A question is solved.");
    }
}

//concrete assignment type: examples.
class example implements assignment
{
    @Override
    public void display(context x)
    {
        //Reference the example
        System.out.println("An example is referenced.");
    }
}

public class one {
    public static void main(String[] args) {
        context q = new context(); //context class to set type and call method
        q.display();
        q.display();
        q.setType(new example());
        q.display();
        q.display();
    }
}
