import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.time.LocalDate;

public class SolarPanelAngleCalculator {
    static ServerSocket Server_Socket;
    static Socket Real_Socket;
    static double Tilt_Angle =0, Azimuth_Angle =0; //to access in other functions

    public static void main(String[] args) {

        JFrame frame = new JFrame("Solar Power Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Container c = frame.getContentPane();
        c.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel title = new JLabel("Solar Power");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 15));
        formPanel.setPreferredSize(new Dimension(400, 250));

        JLabel dayLabel = new JLabel("Day:");
        JTextField dayField = new JTextField(10);

        JLabel hourLabel = new JLabel("Hour:");
        JTextField hourField = new JTextField(10);

        JLabel monthLabel = new JLabel("Month:");
        JTextField monthField = new JTextField(10);

        JLabel latitudeLabel = new JLabel("Latitude:");
        JTextField latitudeField = new JTextField(10);

        JLabel resultLabel = new JLabel("Result:");
        JTextField resultField = new JTextField(10);
        resultField.setEditable(false);

        formPanel.add(dayLabel);
        formPanel.add(dayField);

        formPanel.add(hourLabel);
        formPanel.add(hourField);

        formPanel.add(monthLabel);
        formPanel.add(monthField);

        formPanel.add(latitudeLabel);
        formPanel.add(latitudeField);

        formPanel.add(resultLabel);
        formPanel.add(resultField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 15));
        JButton sendButton = new JButton("Calculate");
        JButton pushButton = new JButton("Push");

        buttonPanel.add(sendButton);
        buttonPanel.add(pushButton);

        JLabel footer = new JLabel("TEAM SEMICOLON GEEKS", JLabel.CENTER);
        footer.setFont(new Font("Arial", Font.BOLD, 14));

        c.add(title);
        c.add(formPanel);
        c.add(buttonPanel);
        c.add(footer);

        sendButton.addActionListener(e -> {
            try {
                int day_of_month = Integer.parseInt(dayField.getText());
                int hour_of_day = Integer.parseInt(hourField.getText());
                int month_of_year = Integer.parseInt(monthField.getText());
                double latitude = Double.parseDouble(latitudeField.getText());

                LocalDate date = LocalDate.of(2024, month_of_year, day_of_month);
                int dayOfYear = date.getDayOfYear();

                Tilt_Angle = calculateOptimalTiltAngle(dayOfYear, latitude);
                Azimuth_Angle = calculateOptimalAzimuthAngle(dayOfYear, hour_of_day, latitude);

                resultField.setText(String.format("Tilt: %.2f°, Azimuth: %.2f°", Tilt_Angle, Azimuth_Angle));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input! Please enter valid numeric values.");
            }
        });


        pushButton.addActionListener(e -> PublishToNodes());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try
        {
            Server_Socket = new ServerSocket(8080);
            Real_Socket = Server_Socket.accept();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static double calculateOptimalTiltAngle(int dayOfYear, double latitude) {
        LocalDate date = LocalDate.ofYearDay(2024, dayOfYear);
        int month = date.getMonthValue();

        if (month >= 5 && month <= 9) {
            return latitude - 15;
        } else if (month == 3 || month == 4 || month == 10) {
            return latitude;
        } else {
            return latitude + 15;
        }
    }

    public static void PublishToNodes()
    {
        // ServerSocket Server_Socket;
        // Socket Real_Socket;
        try
        {
            // Server_Socket = new ServerSocket(8080);
            // Real_Socket = Server_Socket.accept();
            // System.out.println("Connected");
            PrintWriter Client_Writer = new PrintWriter(Real_Socket.getOutputStream(), true);
            Client_Writer.println(Tilt_Angle);
            Client_Writer.println(Azimuth_Angle);
            Client_Writer.close();
            Server_Socket.close();
            Real_Socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static double calculateOptimalAzimuthAngle(int dayOfYear, int hour, double latitude) {
        double hourAngle = (hour - 12) * 15;
        double declination = calculateDeclination(dayOfYear);
        return calculateSolarAzimuth(declination, hourAngle, latitude);
    }

    public static double calculateDeclination(int dayOfYear) {
        double n = dayOfYear;
        return 23.45 * Math.sin(Math.toRadians((360.0 / 365.0) * (284 + n)));
    }

    public static double calculateSolarAzimuth(double declination, double hourAngle, double latitude) {
        double solarAzimuth = Math.toDegrees(Math.atan2(Math.sin(hourAngle * Math.PI / 180),
                Math.cos(hourAngle * Math.PI / 180) * Math.sin(declination * Math.PI / 180)
                        - Math.tan(latitude * Math.PI / 180) * Math.sin(declination * Math.PI / 180)));
        return solarAzimuth;
    }
}
