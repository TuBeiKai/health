import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
       instance.set(instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),1);
        System.out.println(instance.getTime());
    }
}
