package geekCode01.test1;
public class HelloTest {
    private static int[] numbers = {1, 6, 8};
    public static void main(String[] args) {
        TestAverage testAverage = new TestAverage();
        for (int number : numbers) {
            testAverage.submit(number);
        }
        double avg = testAverage.getAvg();
    }
}

class TestAverage {
    private int count = 0;
    private double sum = 0.0D;
    public void submit(double value){
        this.count ++;
        this.sum += value;
    }
    public double getAvg(){
        if(0 == this.count){ return sum;}
        return this.sum/this.count;
    }
}
