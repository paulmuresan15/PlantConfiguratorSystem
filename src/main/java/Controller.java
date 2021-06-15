
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
   private ArrayList<Sensor> sensors=new ArrayList<>();
   private ArrayList<PlantComponent> plantComponents=new ArrayList<>();
   private ArrayList<User> users=new ArrayList<>();
   private User user;
   private Monitor monitor=new Monitor();
   private GUI gui;
   private JSONArray recordedUsers= new JSONArray();
   public Controller(){
       this.gui=new GUI();
   }
   public void addSensor(Sensor sensor){
       sensors.add(sensor);
   }
   public void login(User user){
       this.user=user;
   }
   public void registerMonitor(Sensor sensor){
       sensor.register(monitor);
   }
   public void addComponent(String name){
       plantComponents.add(new PlantComponent(name));
   }
   public void removeComponent(String name){
       PlantComponent removedComponent = null;
       for(PlantComponent component:plantComponents){
           if(component.getName().equals(name)){
                removedComponent=component;
           }
       }
       if(removedComponent!=null){
           plantComponents.remove(removedComponent);
       }
       else{
           System.out.println("Component not found\n");
       }
   }
   public void parseUserObject(JSONObject user){
       JSONObject userObject=(JSONObject) user.get("user");
       String username=(String) userObject.get("username");
       String password=(String) userObject.get("password");
       users.add(new User(username,password));

   }
   public void addUserObject(User user){
       JSONObject userDetails= new JSONObject();
       userDetails.put("username",user.getUsername());
       userDetails.put("password", user.getPassword());
       JSONObject userObject=new JSONObject();
       userObject.put("user",userDetails);
       recordedUsers.add(userObject);

   }
   public void readJson(){
       JSONParser jsonParser=new JSONParser();
       try (FileReader reader=new FileReader("users.json")){
           recordedUsers=(JSONArray) jsonParser.parse(reader);
           recordedUsers.forEach(us->parseUserObject((JSONObject) us));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ParseException e) {
           e.printStackTrace();
       }

   }
   public void writeToJson(){
       try (FileWriter file = new FileWriter("users.json")) {
           //We can write any JSONArray or JSONObject instance to the file
           file.write(recordedUsers.toJSONString());
           file.flush();

       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   public void initController() throws IOException {
       PrintStream printStream = new PrintStream(new CustomOutputStream(gui.getLogTextArea()));
       System.setOut(printStream);
       System.setErr(printStream);
       File recorderUsersFile=new File("users.json");
       if (!recorderUsersFile.createNewFile()) {
           readJson();
       }
       gui.getLogInButton().addActionListener(e ->{
           if(this.user == null) {
               LoginForm loginForm = new LoginForm();
               loginForm.getLogInRegisterButton().addActionListener(e1 -> {
                   boolean found=false;
                   String username = loginForm.getUsernameTextField().getText();
                   String password = String.valueOf(loginForm.getPasswordField().getPassword());
                   User loggedUser = new User(username, password);
                   if(!users.isEmpty()){
                       for (User recorderUser : users){
                           if(recorderUser.getUsername().equals(username) && recorderUser.getPassword().equals(password)){
                               found=true;
                               break;
                           }
                       }
                   }
                   if(users.isEmpty() || !found) {
                       System.out.println("New user created\n");
                       addUserObject(loggedUser);
                       writeToJson();
                       readJson();
                   }
                   System.out.println("Welcome " + username);
                   login(loggedUser);
                   loginForm.getFrame().setVisible(false);
                   gui.getUserTextField().setText(this.user.getUsername());

               });
           }
       });
       gui.getLogOutButton().addActionListener(e -> {
           if(this.user!=null){
             this.user=null;
             gui.getUserTextField().setText("");
               System.out.println("You have been logged out\n");
           }
       });
       gui.getAddNewPlantComponentButton().addActionListener(e -> {
           if(this.user!=null) {
               String plantComponentName = gui.getAddComponentTextField().getText();
               if (!plantComponentName.equals("")) {

                  addComponent(plantComponentName);
                      gui.getComponentsComboBox().addItem(plantComponentName);
                      gui.getSensorComponentsComboBox().addItem(plantComponentName);
                      System.out.println(plantComponentName + " was added as a plant component\n");


               }
           }else
           {
               System.out.println("You need to be logged in for this operation");
           }
       });


       gui.getRemovePlantComponentButton().addActionListener(e -> {
           if(this.user!=null) {
               PlantComponent removedComponent = null;
               String plantComponentName = Objects.requireNonNull(gui.getComponentsComboBox().getSelectedItem()).toString();
               for (PlantComponent component : plantComponents) {
                   if (component.getName().equals(plantComponentName)) {
                       removedComponent = component;
                   }
               }
               if (removedComponent != null) {
                   removeComponent(removedComponent.getName());
                   gui.getComponentsComboBox().removeItem(plantComponentName);
                   gui.getSensorComponentsComboBox().removeItem(plantComponentName);
                   System.out.println("Plant component " + removedComponent.getName() + " has been removed\n");
               } else {
                   System.out.println("Component was not found\n");
               }
           }else
           {
               System.out.println("You need to be logged in for this operation");
           }
       });
       gui.getAddSensorButton().addActionListener(e -> {
           if(this.user!=null) {
               if (!gui.getSensorIdTextField().getText().equals("")) {
                   Sensor sensor = new Sensor(gui.getSensorIdTextField().getText());
                   for (PlantComponent component : plantComponents) {
                       if (Objects.equals(gui.getSensorComponentsComboBox().getSelectedItem(), component.getName())) {
                           component.getSensors().add(sensor);
                           sensors.add(sensor);
                           registerMonitor(sensor);
                           gui.getChangeStateComboBox().addItem(sensor.getId());
                           System.out.println("Sensor " + sensor.getId() + " has been added to component " + component.getName());
                           break;
                       }
                   }
               }
           }
           else
           {
               System.out.println("You need to be logged in for this operation");
           }
       });
       gui.getSensorComponentsComboBox().addItemListener(e -> {
           String componentName= Objects.requireNonNull(gui.getSensorComponentsComboBox().getSelectedItem()).toString();
           for(PlantComponent component : plantComponents){
               if(component.getName().equals(componentName)){
                   if(component.getSensors()!=null) {
                       gui.getChangeStateComboBox().removeAllItems();
                       for (Sensor sensor : component.getSensors()) {
                           gui.getChangeStateComboBox().addItem(sensor.getId());
                       }
                   }
               }
           }
       });
       gui.getCheckStateButton().addActionListener(e->{
           String selectedSensorId = Objects.requireNonNull(gui.getChangeStateComboBox().getSelectedItem()).toString();
           String selectedSensorState = "UNKNOWN_STATE";
           for (Sensor sensor : sensors) {
               if (sensor.getId().equals(selectedSensorId)) {
                   selectedSensorState = sensor.getState();
               }
           }
           System.out.println("Sensor " + selectedSensorId + " is in " + selectedSensorState);
       });
       gui.getChangeStateButton().addActionListener(e -> {
           if(this.user!=null) {
               String selectedSensorId = Objects.requireNonNull(gui.getChangeStateComboBox().getSelectedItem()).toString();
               Sensor selectedSensor = null;
               if (sensors != null) {
                   for (Sensor sensor : sensors) {
                       if (selectedSensorId.equals(sensor.getId())) {
                           selectedSensor = sensor;
                           break;
                       }
                   }
               } else {
                   System.out.println("There are no registered sensors for this component\n");
               }
               if (selectedSensor != null) {
                   if (selectedSensor.getState().equals("SAFE_STATE")) {
                       selectedSensor.dangerState();
                   } else {
                       selectedSensor.safeState();
                   }
               }
           }
           else
           {
               System.out.println("You need to be logged in for this operation");
           }
       });
       gui.getClearLogButton().addActionListener(e -> {
           gui.getLogTextArea().setText("");
       });
   }


}
class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
