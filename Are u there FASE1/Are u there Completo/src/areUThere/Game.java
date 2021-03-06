/*
@author David Acevedo Villarreal
        A01196678
@author Rodrigo Enrique Urbina De la Cruz
        A01281933
@author Raul Ernesto Herrera Salinas
        A00820257
@author Ulises Serrano Martinez
        A01233000
 */
package areUThere;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {

    private BufferStrategy bs;                          // to have several buffers when displaying
    private Graphics g;                                 // to paint objects
    private Display display;                            // to display in the game
    String title;                                       // title of the window
    private int width;                                  // width of the window
    private int height;                                 // height of the window
    private Thread thread;                              // thread to create the game
    private boolean running;                            // to set the game
    private KeyManager keyManager;                      // to manage the keyboard
    private Player player;                              // to use a player
    private ArrayList<Room> rooms;                      // to store rooms
    private int currentRoom;                            // to save current room
    private Enemy enemy;                                // to use a enemy
    private boolean interacting;                         // to determine intersections
    private TestObstruction currentInteraction;         // to determine which textbox to display on collision
    int changeRoomFrameCount;       
    boolean changingRoom;
    int changingRoomAlpha;
    Door selectedDoor;
    int changingRoomAux;

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
     
    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    public boolean isInteracting() {
        return interacting;
    }

    public void setInteracting(boolean interacting) {
        this.interacting = interacting;
    }

    public TestObstruction getCurrentInteraction() {
        return currentInteraction;
    }

    public void setCurrentInteraction(TestObstruction currentInteraction) {
        this.currentInteraction = currentInteraction;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public Player getPlayer() {
        return player;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        player = new Player(getWidth()/2, getHeight()/2, 1, 64, 96, this);
        rooms = new ArrayList<Room>();
        currentRoom = 1;
        interacting = false;
        enemy = new Enemy(300, 110, 50, 50, this);
        display.getJframe().addKeyListener(keyManager);
        currentRoom = 0;
        changingRoom = false;
        interacting = false;
        changingRoomAlpha = 1;
        changingRoomAux = 1;
        
        
//----------------ROOMS------------------
        //Falta corregir las conexiones.
        ArrayList<String> temp;
        
    //----------0. FrontYard
        //Initialize room
        rooms.add(new Room(1024,1536, this));
        
        //Add walls
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,492,352,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(672,492,352,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(352,428,32,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(640,428,32,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(384,364,64,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(576,364,64,20));
        
        //Add doors
        rooms.get(rooms.size() - 1).getDoors().add(new Door(448, 224, 128, 160, 1, 512, 384)); // to lobby
        
    //----------1. Lobby
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(576,300,448,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,576,1024,20));
        
        //Add doors
        rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 512, 384));     // to 2. kitchen
        rooms.get(rooms.size() - 1).getDoors().add(new Door(1008, 420, 16, 64, 1, 512, 384));  // to 8. union room
        rooms.get(rooms.size() - 1).getDoors().add(new Door(448, 224, 128, 96, 4, 512, 640));  // to 4. great hall room
        rooms.get(rooms.size() - 1).getDoors().add(new Door(448, 560, 128, 16, 0, 0, 0));  // to 0. frontyard
        
        //Add objects
        temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));
        
    //----------2. Kitchen *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(384,512, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------3. Dining room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(576,576, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------4. Great Hall Room
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,160,352,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(672,160,352,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(352,64,32,192));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(640,64,32,192));
        
        //Add doors
        rooms.get(rooms.size() - 1).getDoors().add(new Door(384, 0, 256, 64, 0, 0, 0));  // to 16. upper hallway
        rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 352, 16, 64, 3, 0, 0));   // to 3. dining room
        rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 544, 16, 64, 2, 0, 0));  // to 2. kitchen
        rooms.get(rooms.size() - 1).getDoors().add(new Door(1008, 352, 16, 64, 5, 0, 0));  // to 5. library
        rooms.get(rooms.size() - 1).getDoors().add(new Door(1008, 544, 16, 64, 7, 0, 0));  // to 7. hallway
        rooms.get(rooms.size() - 1).getDoors().add(new Door(448, 752, 64, 16, 1, 512, 384));  // to 1. lobby
        
        
        //Add objects
        temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));
        
    //----------5. Library
        //Initialize room
        rooms.add(new Room(1024,1536, this));
        
        //Add walls
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,256,400,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(624,256,400,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(96,204,128,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(448,204,128,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(800,204,128,20));
        
        //Add doors
        rooms.get(rooms.size() - 1).getDoors().add(new Door(0,700,16,64, 0, 0, 0)); // to
        
        //Add objects
        temp = new ArrayList<String>();
        temp.add("Reading table");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(320,640,384,128,temp,this));
        temp = new ArrayList<String>();
        temp.add("Chair");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(272,640,48,112,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(704,640,48,112,temp,this));
        temp = new ArrayList<String>();
        temp.add("Just some old books");
        //Books upstairs
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(0,0,96,224,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(224,0,224,224,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(576,0,224,224,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(928,0,96,224,temp,this));
        //Books floor wall
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(0,256,416,256,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(608,256,416,256,temp,this));
        
    //----------6. Restroom *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(384,384, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------7. Hallway *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(1024,256, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------8. Union room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(256,768, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------9. Toy room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------10. CandyRoom
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(300,192,20,384));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(704,192,20,384));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(300,172,424,20));
        rooms.get(rooms.size() - 1).getWalls().add(new Wall(300,576,424,20));
        
        //Add doors
        rooms.get(rooms.size() - 1).getDoors().add(new Door(320, 384, 16, 64, 0, 0, 0)); // to 8. union room
        
        //Add objects
        temp = new ArrayList<String>();
        temp.add("Candy racks");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(320,192,112,160,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(464,192,96,160,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(592,192,112,160,temp,this));
        temp = new ArrayList<String>();
        temp.add("Gum machine");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(352,448,96,128,temp,this));
        temp = new ArrayList<String>();
        temp.add("Cotton candy");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(576,448,128,128,temp,this));
        
    //----------11. Clothing room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(384,384, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------12. Chem lab *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(512,384, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------13. Venom room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(512,384, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------14. Dark maze *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------15. Torture room *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(1024,768, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------16. Upper hallway *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(1024,256, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------17. Generic bedroom *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(512,512, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------18. Study *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(512,512, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------19. Master bedroom *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(768,768, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    //----------20. Attic *PLACEHOLDER*
        //Initialize room
        rooms.add(new Room(384,512, this));
        
        //Add walls
        //rooms.get(rooms.size() - 1).getWalls().add(new Wall(0,300,448,20));
        
        //Add doors
        //rooms.get(rooms.size() - 1).getDoors().add(new Door(0, 420, 16, 64, 1, 0, 0));     // to
        
        //Add objects
        /*temp = new ArrayList<String>(); //object text
        temp.add("Sofa");
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(96,304,256,96,temp,this));
        rooms.get(rooms.size() - 1).getObstructions().add(new TestObstruction(672,304,256,96,temp,this));*/
        
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 24;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        
        if (interacting) { // interacting with object
            currentInteraction.textBox.tick();
        } else if (changingRoom) { //crossed a door, fade in/out kicks in
            if (changingRoomAlpha >= 255) {
                changingRoomAlpha = 254;
                currentRoom = selectedDoor.getNextRoom();
                player.setX(selectedDoor.getNextX());
                player.setY(selectedDoor.getNextY());
                selectedDoor = null;
                changingRoomAux = -1;
            } else if (changingRoomAlpha <= 0) {
                changingRoom = false;
                changingRoomAlpha = 1;
                changingRoomAux = 1;
            } else {
               changingRoomAlpha += (5 * changingRoomAux);
            }
        } else {
            player.tick();
            //enemy.tick();
            
            //Check if player or enemy intersects with an obstruction
            for(TestObstruction o: rooms.get(currentRoom).getObstructions()) {
                o.interacts(player, g);
                if(player.intersects(o)) {
                    if (player.getDirection() == 1) {
                        player.setY(player.getY() + 32);
                    } else if (player.getDirection() == 2) {
                        player.setX(player.getX() - 32);
                    } else if (player.getDirection() == 3) {
                        player.setY(player.getY() - 32);
                    } else if (player.getDirection() == 4) {
                        player.setX(player.getX() + 32);
                    }
                }
                
                if(enemy.intersects(o)) {
                    if (enemy.getDirection() == 1) {
                        enemy.setY(enemy.getY() + 32);
                    } else if (enemy.getDirection() == 2) {
                        enemy.setX(enemy.getX() - 32);
                    } else if (enemy.getDirection() == 3) {
                        enemy.setY(enemy.getY() - 32);
                    } else if (player.getDirection() == 4) {
                        enemy.setX(enemy.getX() + 32);
                    }
                }
            }
            
            //Check if player intersects with a door
            for (Door d : rooms.get(currentRoom).getDoors()) {
                if (d.intersects(player)) { //esto esta aqui para checar si el jugador cruza una puerta 
                    selectedDoor = d;
                    changingRoom = true;
                }
            } 
            
            //Check if player intersects with a wall
            for (Wall w : rooms.get(currentRoom).getWalls()) {
                if(player.intersects(w)) {
                    if (player.getDirection() == 1) {
                        player.setY(player.getY() + 32);
                    } else if (player.getDirection() == 2) {
                        player.setX(player.getX() - 32);
                    } else if (player.getDirection() == 3) {
                        player.setY(player.getY() - 32);
                    } else if (player.getDirection() == 4) {
                        player.setX(player.getX() + 32);
                    }
                }
            }
        }
    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.setColor(new Color(0,0,0));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            rooms.get(currentRoom).render(g);
            player.render(g);
            enemy.render(g);
            if(interacting) {
                currentInteraction.textBox.render(g);
            } else if (changingRoom) {
                if (changingRoomAlpha > 255) {
                    changingRoomAlpha = 255;
                } else if (changingRoomAlpha < 0) {
                    changingRoomAlpha = 0;
                }
                System.out.println(changingRoomAlpha);
                g.setColor(new Color(0,0,0,changingRoomAlpha));
                g.fillRect(0,0,getWidth(), getHeight());
            }
            bs.show();
            g.dispose();
        }
    }
    
    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
