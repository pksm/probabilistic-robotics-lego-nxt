import lejos.nxt.Motor;
import lejos.robotics.navigation.*;
import lejos.robotics.localization.OdometryPoseProvider;

public class Navigator2 {
    //DifferentialPilot(diametro_roda, largura_robo, ...)
    static Navigator navigator;
    static OdometryPoseProvider position;

    public static void main(String[] args) {
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B, true);
        position = new OdometryPoseProvider (pilot);
        navigator = new Navigator (pilot, position);

        pilot.setTravelSpeed(20);  // cm por segundo
        pilot.setRotateSpeed(45);  // graus por segundo

         // Não é necessário
        for (int i = 0; i < 2; i++) {
            Move (50.0f, 0.0f);
            Move (50.0f, 50.0f);
            Move (0.0f, 50.0f);
            Move (0.0f, 0.0f);
            Pose tmp = new Pose(0.0f, 0.0f, 0.0f);
            position.setPose(tmp);
        }
    }

    private static void Move (float x, float y) {
        navigator.goTo(x, y);
        // navigator.goTo(x, y, ang);
        while (navigator.isMoving()) {
            //Mostra a Pose
            System.out.println(position.getPose());
        }
    }
}
