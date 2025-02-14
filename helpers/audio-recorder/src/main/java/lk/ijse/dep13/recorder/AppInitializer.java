package lk.ijse.dep13.recorder;

import javax.sound.sampled.LineUnavailableException;

public class AppInitializer {
    public static void main(String[] args) throws LineUnavailableException {
        AudioRecorder recorder = new AudioRecorder();
        System.out.println("Recording");
        recorder.record(5);
        System.out.println("Playing");
        recorder.play();
    }
}
