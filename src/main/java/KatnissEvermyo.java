import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import util.MyoDataCollector;
import util.EmgDataCollector;

public class KatnissEvermyo {
    public static void main(String[] args) {
        try {
            Hub hub = new Hub("com.example.emg-data-sample");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband!");
            myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
            DeviceListener emgCollector = new EmgDataCollector();
            DeviceListener dataCollector = new MyoDataCollector();
            hub.addListener(emgCollector);
            hub.addListener(dataCollector);

            while (true) {
                hub.run(1000 / 20);
                System.out.println(dataCollector);
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}