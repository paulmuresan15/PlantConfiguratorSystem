import java.util.ArrayList;

public class PlantComponent {
    private String name;
    private ArrayList<Sensor> sensors=new ArrayList<>();

    public PlantComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
}
