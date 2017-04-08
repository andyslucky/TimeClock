import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;
/**
 * Created by Andrew on 1/7/2017.
 * @version 1.20
 */
public class TimeClock extends JFrame {
    private static JFrame frame = new JFrame("TimeClock");
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton button1;
    public JEditorPane editorPane1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton button2;
    private JEditorPane editorPane2;
    private JPasswordField passwordField2;
    private JTextField textField3;
    private JButton button3;
    private JPasswordField passwordField3;
    private JButton changeId;
    private JButton changeIDButton;
    private static final ArrayList<String> userList = new ArrayList<>();
    private static ArrayList<String> passwordList = new ArrayList<>();
    private static BufferedWriter bw;
    private static File data = new File("data.csv");
    private int lineCount = 0;

    TimeClock() throws FileNotFoundException {

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if(passwordList.contains(passwordField3.getText())) {
                    setMainText(userList.get(passwordList.indexOf(passwordField3.getText())) +  TimeKeeper.routing(passwordField3.getText()));
                }else{
                   setMainText("Sorry! Incorrect Password! Try Again!");
                }


            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!passwordList.contains(passwordField1.getText())) {
                    try {
                        if(!passwordField1.getText().equals(passwordField2.getText())){
                            JOptionPane.showMessageDialog(null, "Sorry, ID fields do not match!");
                        }else {
                            final String NEWLINE = System.getProperty("line.separator");
                            FileWriter out = new FileWriter(data);
                            bw = new BufferedWriter(out);
                            passwordList.add(passwordField1.getText());
                            userList.add(textField2.getText());
                            int index = 0;
                            while (index < passwordList.size() && index < userList.size()) {
                                bw.write(passwordList.get(index) + "," + userList.get(index) + NEWLINE);
                                index++;
                            }
                            bw.close();
                            readIn();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Sorry, that ID has already been taken!");
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPane2.setText("");
                File record = new File("Records/"+textField3.getText() + ".csv");
                if(record.exists()){
                    try {
                        Scanner recordText = new Scanner(record);
                        while(recordText.hasNext()) {
                            setRecordText(recordText.nextLine());
                        }
                    }catch(IOException ex){

                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Sorry the file could not be found!");
                }
            }
        });
        passwordField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordList.contains(passwordField3.getText())) {
                    setMainText(userList.get(passwordList.indexOf(passwordField3.getText())) +  TimeKeeper.routing(passwordField3.getText()));
                }else{
                    setMainText("Sorry! Incorrect Password! Try Again!");
                }

            }
        });
        changeId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        final String NEWLINE = System.getProperty("line.separator");
                        FileWriter out = new FileWriter(data);
                        bw = new BufferedWriter(out);
                        passwordList.add(userList.indexOf(textField2.getText()), passwordField2.getText());
                        int index = 0;
                        while (index < passwordList.size() && index < userList.size()) {
                            bw.write(passwordList.get(index) + "," + userList.get(index) + NEWLINE);
                            index++;
                        }
                        bw.close();
                        readIn();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void setMainText(String input){
        int lineHeight = editorPane2.getFont().getSize();
        if(editorPane1.getText().equals("") ){
            editorPane1.setText(input);
            lineCount++;

        }else if(lineCount == ((editorPane2.getSize().height)/lineHeight)-20 ){
            editorPane1.setText(input);
            lineCount = 1;
        }
        else {
            editorPane1.setText(editorPane1.getText() + "\n" + input);
            lineCount++;
        }
    }
    public void setRecordText(String input){
        if(editorPane2.getText().equals("") ){
            editorPane2.setText(input);
        }
        else {
            editorPane2.setText(editorPane2.getText() + "\n" + input);
        }
    }
    public static void main(String[] args) throws IOException {
        TimeClock tc = new TimeClock();

        frame.setContentPane(tc.panel1);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        tc.readIn();

    }


    private void readIn() throws IOException {
        passwordList.clear();
        userList.clear();
        Scanner breakUp;
        if (data.exists()) {
            FileReader in = new FileReader(data);
            Scanner readLine = new Scanner(data);
            do {
                String line = readLine.nextLine();
                try {
                    breakUp = new Scanner(line);
                    breakUp.useDelimiter(",");
                    passwordList.add(breakUp.next());
                    userList.add(breakUp.next());
                } catch (NullPointerException ex) {
                    break;
                }
            } while (readLine.hasNextLine());
            TimeKeeper.loadUsers(userList, passwordList);
        }
        setMainText("Users updated: " + userList.size() + " Users.");
        lineCount++;
    }

    }