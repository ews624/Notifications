/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    private Notifiable notificationTarget;
    
    @FXML
    Text task1Status;
   
    
    @FXML
    Text task2Status;
    
    @FXML
    Text task3Status;
    
    
    @FXML
    Button task1Text;
    
    @FXML
    Button task2Text;
    
    @FXML
    Button task3Text;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            task1Status.setText("Not Running");
            task2Status.setText("Not Running");
            task3Status.setText("Not Running");

    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1Status.setText("Running");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.setRunning(this);
            task1.start();
            if(task1.running() == true){
                task1Status.setText("Finished");
            }
            task1Text.setText("End Task 1");
        }
        
        else{
            task1Status.setText("Stopped");
            task1.end();
            task1Text.setText("Start Task 1");
            task1 = null;
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2Status.setText("Running");
            task2Text.setText("End Task 1");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            task2.start();
        }
        else {
            task2Status.setText("Stopped");
            task2.end();
            task2Text.setText("Start Task 1");
            task2 = null;
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3Status.setText("Running");
            task3Text.setText("End Task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
              
            
            });
            
            task3.start();
        }
        else{
            task3Status.setText("Stopped");
            task3.end();
            task3Text.setText("Start Task 3");
            task3 = null;
        }
    } 
}
