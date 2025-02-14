package lk.ijse.dep13.recorder;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioRecorder {

    private volatile boolean stop = false;
    private AudioFormat format;
    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private ByteArrayOutputStream buffer;

    public AudioRecorder() throws LineUnavailableException {
        format = new AudioFormat(44100, 16, 2, true, false);
        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
        DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(targetInfo);
        speakers = (SourceDataLine) AudioSystem.getLine(sourceInfo);
        microphone.open(format);
        speakers.open(format);
    }

    public void record(int durationInSeconds) {
        microphone.start();
        new Thread(()-> {
            try {
                Thread.sleep(durationInSeconds * 1000);
                stop = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        buffer = new ByteArrayOutputStream();
        while (!stop) {
            byte[] data = new byte[microphone.getBufferSize()];
            int read = microphone.read(data, 0, data.length);
            buffer.write(data, 0, read);
        }
        try {
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (buffer == null) throw new IllegalStateException("No audio found");
        speakers.start();
        speakers.write(buffer.toByteArray(), 0, buffer.size());
    }
}
