import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


class TimeKeeper {
    private static ArrayList<String> loggedIn = new ArrayList<>();
    private static ArrayList<Long> times = new ArrayList<>();
    private static ArrayList<String> usersList= new ArrayList<>();
    private static ArrayList<String> passwordList= new ArrayList<>();
    private static File records = new File("Records/records.csv");
    private static Calendar now = Calendar.getInstance();
    private static long yourmilliseconds = System.currentTimeMillis();
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
    private static Date resultdate = new Date(yourmilliseconds);
    private static String date = sdf.format(resultdate);
    private static final String NEWLINE = System.getProperty("line.separator");
    static void loadUsers(ArrayList<String> users, ArrayList<String> id){
        usersList = users;
        passwordList = id;
    }
    static String routing(String id){
        if(loggedIn.contains(id)){
            return logout(id);
        }else{
            return login(id);
        }
    }
    private static String login(String id){
        long timeIn = System.currentTimeMillis();
        times.add(timeIn);
        loggedIn.add(id);
        return ": logged in!";
    }
    private static String logout(String id){

        long timeOut = System.currentTimeMillis();
        int index = loggedIn.indexOf(id);
        long total =(timeOut -times.get(index));
        long time = (total/1000);
        writeTime(Double.parseDouble(String.format("%.3f", (time /60.0 /60.0))), id);
        loggedIn.remove(index);
        times.remove(index);
        if((now.get(Calendar.DAY_OF_WEEK) == GregorianCalendar.THURSDAY) && loggedIn.isEmpty()){
            closeWeek();
        }
        
        return " worked a total of: " + String.format("%.3f", (time /60.0 /60.0)) + " hours";
    }
    private static void writeTime(double time,String id ) {
        File recDir = new File("Records");
        if (!recDir.exists()) {
            recDir.mkdir();
        }
        while (true) {

            try {

                boolean addTime = false;
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
                        if(!addTime) {
                            time = time + Double.parseDouble(hours.get(index));
                            hours.set(index, Double.toString(time));
                            addTime = true;
                        }
                    }
                    if(!addTime) {
                        index = names.indexOf(name);
                        time = time + Double.parseDouble(hours.get(index));
                        hours.set(index, Double.toString(time));
                        addTime = true;
                    }
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
        try{

            Scanner input = new Scanner(records);
            PrintStream ps = new PrintStream(new File("Records/"+date+".csv"));
            ps.println("DATE: "+date);
            ps.println("-----------------------");
            String contents = "";
            while(input.hasNextLine()){
                String line =input.nextLine();
                contents+=line+"\n";
                ps.println(line);
            }
            ps.close();
            double[] newHours = new double[usersList.size()];
            int index = 0;
            BufferedWriter bw = new BufferedWriter(new FileWriter(records));
            while(index < usersList.size() && index < newHours.length){
                bw.write(usersList.get(index) + "," + newHours[index] + NEWLINE);
                index++;
            }
            bw.close();
            JOptionPane.showMessageDialog(null, contents,"Hours for: "+date,
                    JOptionPane.PLAIN_MESSAGE);
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Sorry could not find the specified file!");
        }
    }
}
