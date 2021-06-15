public class Sensor extends Observable{
     private String id;
     private String state;
    public Sensor(String id) {
        this.id = id;
        this.state="SAFE_STATE";
    }

    public String getId() {
        return id;
    }

    public void safeState(){
        this.changeState("SAFE_STATE",this.id);
        this.state="SAFE_STATE";
    }

    public void dangerState(){
        this.changeState("DANGER_STATE",this.id);
        this.state="DANGER_STATE";
    }

    public String getState() {
        return state;
    }
}
