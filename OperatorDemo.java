public class OperatorDemo{
    public static void main(String[]args){
        int a = 11;
        int b = 22;
        int c = 15;

        if(a<b){
            c++;
        }
        if(c>0){
            c--;
        }
        else{
            c = 3;
        }

        System.out.println("+a =" + a);
        System.out.println("+b =" + b);
        System.out.println("+c =" + c);
    }
}