public class Monitor implements Observer{


    @Override
    public void update(Object event, String id) {
        System.out.println("Sensor "+id+" has changed state: "+ event);
    }
}
