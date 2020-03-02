import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readTemp {
    public static void main(String [] args) {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH);
        pin.setShutdownOptions(true, PinState.LOW);

        boolean fanStat=false;
        pin.low();

        String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                double tempC = (Integer.parseInt(line) / 1000);
                if(tempC>47.0){
                    if(fanStat==false)
                    {
                        pin.toggle();
                        fanStat=true;
                    }
                }
                else if(tempC<46.0){
                    if(fanStat==true)
                    {
                        pin.toggle();
                        fanStat=false;
                    }
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}
