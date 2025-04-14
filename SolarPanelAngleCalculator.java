import java.time.LocalDateTime;
import java.time.ZoneId;

public class SolarPanelAngleCalculator {

    public static final double SUN_LATITUDE = 30.35;
    public static final ZoneId SUKKUR_ZONE = ZoneId.of("Asia/Karachi");


    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now(SUKKUR_ZONE);
        int dayOfYear = now.getDayOfYear();
        int hour = now.getHour();

        double tiltAngle = calculateOptimalTiltAngle(dayOfYear);
        double azimuthAngle = calculateOptimalAzimuthAngle(dayOfYear, hour);


        System.out.println("Optimal Tilt Angle (degrees): " + tiltAngle);
        System.out.println("Optimal Azimuth Angle (degrees): " + azimuthAngle);
    }

    public static double calculateOptimalTiltAngle(int dayOfYear) {

        if (dayOfYear >= 172 && dayOfYear <= 275) {
            return SUN_LATITUDE - 15;
        } else {
            return SUN_LATITUDE + 15;
        }
    }

    public static double calculateOptimalAzimuthAngle(int dayOfYear, int hour) {

        double hourAngle = (hour - 12) * 15;
        double declination = calculateDeclination(dayOfYear);

        double solarAzimuth = calculateSolarAzimuth(declination, hourAngle, SUN_LATITUDE);

        return solarAzimuth;
    }

    public static double calculateDeclination(int dayOfYear) {
        double n = dayOfYear;
        double declination = 23.45 * Math.sin(360 / 365 * (284 + n) * Math.PI / 180);
        return declination;
    }

    public static double calculateSolarAzimuth(double declination, double hourAngle, double latitude) {
        double solarElevation = calculateSolarElevation(declination, hourAngle, latitude);

        double solarAzimuth = Math.toDegrees(Math.atan2(Math.sin(hourAngle * Math.PI / 180),
                Math.cos(hourAngle * Math.PI / 180) * Math.sin(declination * Math.PI / 180)
                        - Math.tan(latitude * Math.PI / 180) * Math.sin(declination * Math.PI / 180)));
        return solarAzimuth;
    }

    public static double calculateSolarElevation(double declination, double hourAngle, double latitude) {
        double solarElevation = Math.toDegrees(Math.asin(Math.sin(declination * Math.PI / 180) * Math.sin(latitude * Math.PI / 180) +
                Math.cos(declination * Math.PI / 180) * Math.cos(latitude * Math.PI / 180) * Math.cos(hourAngle * Math.PI / 180)));
        return solarElevation;
    }
}