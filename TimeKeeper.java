import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class TimeKeeper {
    public static ArrayList<String> loggedIn = new ArrayList<>();
    private static ArrayList<BigDecimal> times = new ArrayList<>();
    private static ArrayList<String> usersList= new ArrayList<>();
    private static ArrayList<String> passwordList= new ArrayList<>();
    private static File records = new File("Records/records.csv");
    
    public static void loadUsers(ArrayList users, ArrayList id){
        usersList = users;
        passwordList = id;
    }
    public static String routing(String id){
        if(loggedIn.contains(id)){
            return logout(id);
        }else{
            return login(id);
        }
    }
    private static String login(String id){
        BigDecimal timeIn = new BigDecimal(System.currentTimeMillis());
        times.add(timeIn);
        loggedIn.add(id);
        return ": logged in!";
    }
    private static String logout(String id){
        BigDecimal timeOut = new BigDecimal(System.currentTimeMillis());
        int index = loggedIn.indexOf(id);
        BigDecimal total =(timeOut.subtract(times.get(index)));
        long time = (total.longValue()/1000);
        writeTime(Double.parseDouble(String.format("%.3f", ((double)(time /60.0)/60.0))), id);
        loggedIn.remove(index);
        times.remove(index);
        
        return " worked a total of: " + String.format("%.3f", ((double)(time /60.0)/60.0)) + " hours";
    }
    private static void writeTime(double time,String id ) {
        File recDir = new File("Records");
        if (!recDir.exists()) {
            recDir.mkdir();
        }
        while (true) {

            try {
                final String NEWLINE = System.getProperty("line.separator");

                ArrayList<String> names = new ArrayList<>();
                ArrayList<String> hours = new ArrayList<>();
                if (records.exists()) {
                    names.clear();
                    hours.clear();
                    Scanner fileIn = new Scanner(records);
                    Scanner token;
                    while (fileIn.hasNextLine()) {
                        try {
                            token = new Scanner(fileIn.nextLine());
                            token.useDelimiter(",");
                            names.add(token.next());
                            hours.add(token.next());
                        } catch (NullPointerException ex) {
                            break;
                        }
                    }
                    String name = usersList.get(passwordList.indexOf(id));
                    int index = 0;
                    if(!names.contains(name)) {
                        names.add(name);
                        hours.add("0.0");
                        index = names.indexOf(name);
                    }
                    time = time + Double.parseDouble(hours.get(index));
                    hours.set(index, Double.toString(time));
                    index = 0;
                    BufferedWriter bw = new BufferedWriter(new FileWriter(records));
                    while (index < names.size() && index < hours.size()) {
                        bw.write(names.get(index) + "," + hours.get(index) + NEWLINE);
                        index++;
                    }
                    bw.close();
                    break;
                } else if(!records.exists()){
                    double[] newHours = new double[usersList.size()];
                    int index = 0;
                    BufferedWriter bw = new BufferedWriter(new FileWriter(records));
                    while(index < usersList.size() && index < newHours.length){
                        bw.write(usersList.get(index) + "," + newHours[index] + NEWLINE);
                        index++;
                    }
                    bw.close();
                    continue;
                }

            } catch (IOException ex) {
                break;
            }
            break;
        }
    }
    private static void closeWeek(){
        
    }
}
