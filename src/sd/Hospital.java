package sd;

public class Hospital {
    public static final String ip37 = "10.6.40.177";
    public static final String ip38 = "10.6.40.178";
    public static final String ip39 = "10.6.40.179";
    public static final String ip40 = "10.6.40.180";
    public static void main (String[] args) {
        for (String s: args) {
            if (s.equals("37")) {
                System.out.println(ip37);
            }
            if (s.equals("38")) {
                System.out.println(ip38);
            }
            if (s.equals("39")) {
                System.out.println(ip39);
            }
            if (s.equals("40")) {
                System.out.println(ip40);
            }
        }
    }
}