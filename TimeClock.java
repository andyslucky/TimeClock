import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;


/**
 * Created by Andrew on 1/7/2017.
 * @version 1.10
 */
public class TimeClock extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JTextField textField1;
    public JEditorPane editorPane1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton button2;
    private JEditorPane editorPane2;
    private JPasswordField passwordField2;
    private JTextField textField3;
    private JButton button3;
    private static final ArrayList<String> userList = new ArrayList<>();
    private static ArrayList<String> passwordList = new ArrayList<>();
    private static BufferedWriter bw;
    private static File data = new File("data.csv");
    private int lineCount = 0;

    TimeClock() throws FileNotFoundException {

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if(passwordList.contains(textField1.getText())) {
                    setMainText(userList.get(passwordList.indexOf(textField1.getText())) +  TimeKeeper.routing(textField1.getText()));
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
                            BufferedReader br = new BufferedReader(new FileReader(data));
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
    }
    private void setMainText(String input){
        if(editorPane1.getText().equals("") ){
            editorPane1.setText(input);
            lineCount++;

        }else if(lineCount == 32){
            editorPane1.setText(input);
            lineCount = 1;
        }
        else {
            editorPane1.setText(editorPane1.getText() + "\n" + input);
            lineCount++;
        }
    }
    public static void main(String[] args) throws IOException {
        TimeClock tc = new TimeClock();
        JFrame frame = new JFrame("TimeClock");
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
    }

    }