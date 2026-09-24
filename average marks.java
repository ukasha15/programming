public class average{
    public static void main(String[]args){
        int[] marks = {70,80,65,90,85};
        // total marks
        double sum = 0;

        for(int num : marks){
            sum +=num;



        }

        double average = sum / marks.length;
        System.out.println("Average:" + average);
    }
}