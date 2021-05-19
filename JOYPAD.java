//JOYPAD v1.1
//mar 10 2020
//author kaleab
package joypad;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.*;
import java.net.*;
import java.util.HashMap;//for key maping
import java.util.logging.Level;
import java.util.logging.Logger;
public class JOYPAD {
    connection obj;
    public JOYPAD(){
        obj = new connection();
        obj.start();
    }
    public static void main(String[] args) {
        new JOYPAD();
    }
    class connection extends Thread{
        
        private DataInputStream in;
        private ServerSocket sv;
        private Socket sock;
        private boolean status = false;
        private Robot myrobot;//handles keyboard event
        private HashMap<String,Integer> keymap; //for storing keys
        private String help = "configure your (ingame)control buttons similar to the keys below\n"
                + "inorder for the program to work properly:"
                + "accelerate = W\n"
                + "brake = S\n"
                + "handbrake = B\n"
                + "nitro = N"
                + "right = D\n"
                + "left = A\n";
        public connection(){
            keymap = new HashMap<String,Integer>();
            keymap.put("w",87);//accelerate
            keymap.put("s",83);//brake
            keymap.put("a",65);//left
            keymap.put("d",68);//right
            keymap.put("b",66);//hand brake
            keymap.put("n",78);//nitro
            try{
                myrobot = new Robot();
            }catch(AWTException e){print("faild to initialize try restarting the program");}
            print("joypad for racing games v1.1");
            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                String myip = ip.getHostAddress();
                print(myip);
            } catch (UnknownHostException ex) {
                Logger.getLogger(JOYPAD.class.getName()).log(Level.SEVERE, null, ex);
                print("unable to get public ip address");
            }
            
            print("connect yout phone using the ipaddress above\n\n");
            print(help);
        }
        @Override
        public void run(){
            try{
                
                sv = new ServerSocket(4444);
                sock = sv.accept();
                print("connected");
                status = true;
            }catch(IOException e){
                print("error connecting");
                status = false;
            }
            try{
                in = new DataInputStream(sock.getInputStream());
                String key;
                while(status){
                    if(in.available()!=0){
                        key = in.readUTF();
                        print(key);
                        handlekeyboard(key);
                    }
                }
            }catch(IOException e){}
        }
        private void print(String str){
            System.out.println(str);
        }
        public void handlekeyboard(String key){
            String[] cmd = key.split(" ");
            switch(cmd[0]){
                case "a":
                    if(cmd[1]=="p"){
                        press("a");
                    }else{
                        release("a");
                    }
                    break;
                case "w":
                    if(cmd[1]=="p"){
                        press("w");
                    }else{
                        release("w");
                    }
                    break;
                case "s":
                    if(cmd[1]=="p"){
                        press("s");
                    }else{
                        release("s");
                    }
                    break;
                case "d":
                    if(cmd[1]=="p"){
                        press("d");
                    }else{
                        release("d");
                    }
                    break;
                case "b":
                    if(cmd[1]=="p"){
                        press("b");
                    }else{
                        release("b");
                    }
                    break;
                case "n":
                    if(cmd[1]=="p"){
                        press("n");
                    }else{
                        release("n");
                    }
                    break;
                default:
                    print("invalid key received!!");
                    break;
            }
        }
        public void press(String btn){
            try{
                myrobot.keyPress(keymap.get(btn));
            }catch(Exception e){
                print("error in method press()");
            }
        }
        public void release(String btn){
            try{
                myrobot.keyRelease(keymap.get(btn));
            }catch(Exception e){
                print("error in method release()");
            }
        }
    }
    
}
