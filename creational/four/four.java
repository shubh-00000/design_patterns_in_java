package creational.four;
//sychronized getInstance with Double Locking to ensure only one object gets created
public class four {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                assignment a1 = assignment.getQuestion();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                assignment a1 = assignment.getQuestion();
            }
        });
        t1.start();
        t2.start();
    }    
}      
class assignment{
    public static assignment a; //only initialized the object
    static int i = 1;
    private assignment() {
        System.out.println("Question "+i);
        i++;
    }
    
    public static assignment getQuestion() {
        if(a == null){ //condition to confirm object has not been created earlier   
            synchronized (assignment.class) {
        if(a == null){ //condition to confirm object has not been created earlier- Double Checked Locking
                a = new assignment();} //object created here as a lazier process
            }
    }
        return a;
}
}


