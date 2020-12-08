
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDictionary {
    public static void main(String[] args) throws ParseException {
        String str = "'2020-01-01':4,'2020-01-02':4,'2020-01-03':6,'2020-01-04':8,'2020-01-05':2,'2020-01-06':-6,'2020-01-07':2,'2020-01-08':-2";
        dictionary(str);
        str = "'2020-01-01':6,'2020-01-04':12,'2020-01-05':14,'2020-01-06':2,'2020-01-07':4";
        dictionary(str);
    }

    public static void dictionary(String D) throws ParseException {
        String[] split_dictionary = D.split(",");
        String format = "yyyy-mm-dd";
        String[] arr_week = new String [] {"Mon","Tue","Web","Thu","Fri","Sat","Sun"};
        int[] int_week = new int[8];
        boolean[] is_day_in = new boolean[7];
        for(String s : split_dictionary) {
            String[] s_split = s.split(":");
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = df.parse(s_split[0].replaceAll("'",""));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            is_day_in[week-1] = true;
            int_week[week] += Integer.parseInt(s_split[1]);
        }

        int index=0;
        while(index < 7) {
            int st_index=0;
            while(index<7 && is_day_in[index]) index++;
            st_index=index;
            int en_index=index;
            if(index==7)
                break;
            while(index<7 && !is_day_in[index]) index++;
            en_index=index;
            if(index==7)
                break;
            int gap=0;
            gap = int_week[st_index] > int_week[en_index]?int_week[st_index]-int_week[en_index]:int_week[en_index]-int_week[st_index];
            int gap_diff = gap/( (en_index-st_index) +1);
            for(int j=st_index;j<=en_index;j++) {
                int_week[j+1] = int_week[j]+gap_diff;
            }
        }

        int i=2;
        StringBuilder str_D = new StringBuilder();
        for(String day : arr_week ) {
            str_D.append("'").append(day).append("':").append(int_week[i]);
            if(i!=day.length()-1)
                str_D.append(", ");
            i++;
            if(i==8)
                i=1;
        }
        System.out.println(str_D.substring(0,str_D.length()-2));
    }

}
