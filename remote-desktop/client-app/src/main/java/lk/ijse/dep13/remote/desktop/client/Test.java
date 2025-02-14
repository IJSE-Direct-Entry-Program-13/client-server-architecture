package lk.ijse.dep13.remote.desktop.client;

import java.awt.*;

public class Test {

    public static void main(String[] args) {
        GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        System.out.println(screenDevices[1].getDisplayMode().getWidth());
        System.out.println(screenDevices[0].getDisplayMode().getWidth());
    }
}
