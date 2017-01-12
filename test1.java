import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;


/**
 * Created by Andrew on 1/7/2017.
 */
public class test1 extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JTable table1;
    private JTextArea textArea1;
    private String input;
    w1 class1 = new w1();


    public test1() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = passwordField1.toString();
                if (input == w1.id && w1.logedin == false){class1.login();}

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test1");
        frame.setContentPane(new test1().panel1);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        File dir = new File("WorkerInfo");
        File dir1 = new File("TimeReports");
        JTextArea ta = new JTextArea();
        

        if (dir.exists() == false){
            dir.mkdir();
            dir1.mkdir();

        }


    }
}