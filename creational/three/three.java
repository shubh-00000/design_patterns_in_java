package creational.three;
//abstract factory design   
abstract class Assignment //factory of abstract factory
{
abstract assignment1 getQuestion(int questionNumber);
abstract assignment2 getExample(int exampleNumber);
}
//abstract products
interface assignment1 {
    void solve();
}
interface assignment2 {
    void reference();
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
//abstract factory 1
class Question extends Assignment {
    //getQuestion returns method of assignment1, which is Question 
    public assignment1 getQuestion(int questionNumber){
        if(questionNumber == 1){
            return new question1(); 
        }
        if(questionNumber == 2){
            return new question2(); 
        }
        if(questionNumber == 3){
            return new question3(); 
        }
        return null;
    }
    assignment2 getExample(int exampleNumber) //not implemented
    {return null;}
}
//abstract products
class example1 implements assignment2 {
    @Override
    public void reference() {
        System.out.print("example one was referenced. \n");
    }
}
class example2 implements assignment2 {
    @Override   
    public void reference() {
        System.out.print("example two was referenced. \n");
    }
}
class example3 implements assignment2 {
    @Override
    public void reference() {
        System.out.print("example three was referenced. \n");
    }
}
//abstract factory 2
class Example extends Assignment {
    public assignment2 getExample(int exampleNumber){
        if(exampleNumber == 1){
            return new example1(); 
        }
        if(exampleNumber == 2){
            return new example2(); 
        }
        if(exampleNumber == 3){
            return new example3(); 
        }
        return null;
    }
    assignment1 getQuestion(int questionNumber)
    {return null;}
}

//factory producer
class assignmentFactory {
public static Assignment getChoice(String choice)
{ if(choice.equalsIgnoreCase("Question"))
{
return new Question();
}
else if(choice.equalsIgnoreCase("Example"))
{
return new Example();
}
return null;
} }

public class three {
    public static void main(String[] args) {
        Assignment Question = assignmentFactory.getChoice("Question");
        assignment1 q1 = Question.getQuestion(1);
        q1.solve();
        assignment1 q2 = Question.getQuestion(2);
        q2.solve();
        assignment1 q3 = Question.getQuestion(3);
        q3.solve();
        Assignment Example = assignmentFactory.getChoice("Example");
        assignment2 e1 = Example.getExample(1);
        e1.reference();
        assignment2 e2 = Example.getExample(2);
        e2.reference();
        assignment2 e3 = Example.getExample(3);
        e3.reference();
    }
}
