import javax.swing.*;
import java.awt.*;

public class SolarPower {
    public static void main(String[] args) {
        JFrame frame=new JFrame();
        Container c=frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout bl=new BorderLayout();
        c.setLayout(bl);
        c.setBackground(Color.BLACK);

        JButton send = new JButton("Send");




        JPanel panel=new JPanel();
        JPanel panel2=new JPanel();

        JLabel title=new JLabel("Solar Power");
        JLabel title2=new JLabel("TEAM SEMICOLONGEEKS");
        JLabel DateLab=new JLabel("Date");
        JLabel MonthLab=new JLabel("Month");
        JLabel latitudeLab=new JLabel("Latitude");
        JLabel calculate=new JLabel("calculate");

        JTextField date=new JTextField();
        JTextField month=new JTextField();
        JTextField latitude=new JTextField();
        JTextField result=new JTextField();
        panel.add (DateLab) ;
        panel.add (date) ;

        panel.add (MonthLab) ;
        panel.add (month) ;

        panel.add (latitudeLab) ;
        panel.add (latitude) ;

        panel.add(calculate);
        panel.add(result);

        panel.add(send);






title.setBackground(Color.WHITE);
        title.setForeground(Color.WHITE);

        title2.setForeground(Color.WHITE);

        panel.setLayout(new GridLayout(5,2));

        frame.setBackground(Color.BLUE);





c.add(title,BorderLayout.NORTH);
        c.add(panel,BorderLayout.CENTER);
        c.add(title2,BorderLayout.SOUTH);












        frame.setVisible(true);
        frame.setSize(300,200);
        frame.setBackground(Color.WHITE);

    }
}

