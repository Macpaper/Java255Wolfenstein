package as22;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;

public class App extends JPanel implements Runnable, ActionListener {
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	Dimension size = new Dimension(WIDTH, HEIGHT);
	BorderLayout bl = new BorderLayout();
	Thread thread;
//	Image gif1;
	BufferedImage gif1;
	JPanel jp1;
	public void loadAssets() {
		try {
			// to make this work either delete this or use a gif called funny.gif
			// otherwise enjoy my raycasting doomlike 
//			URL url1 = getClass().getResource("funny.gif");

			URL url1 = new URL("https://www.textures-resource.com/resources/sheets/2/1375.png?updated=1460970129");
			gif1 = ImageIO.read(url1);
			
//			ImageIcon imIcon = new ImageIcon(url1);
//			gif1 = imIcon.getImage();
			
		} catch (Exception e) {
			System.out.println("COULDN'T LOAD IMAGE SRRY!");
			e.printStackTrace();
		}
	}
	enum State {
		HAPPY,
		SAD,
		GIF,
		DOOM,
		NONE
	}
	State currState;
	JButton happy, sad, gif, doom;
	public App() {
		currState = State.NONE;
		setPreferredSize(size);
		setFocusable(true);
		setDoubleBuffered(true);
		
		initPanels();
		loadAssets();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(50, 50, 50));
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		switch(currState) {
			case DOOM:
				break;
			case HAPPY:
				drawHappy(g2);
				break;
			case GIF:
				drawGif(g2);
				break;
			case NONE:
				break;
			case SAD:
				drawSad(g2);
				break;
			default:
				break;
		}
		
		if (currState == State.DOOM) {
			g2.setColor(new Color(50, 50, 50));
			g2.fillRect(0, 0, 800, 600);
			render(g2);
		}
	}
	
	public void drawGif(Graphics2D g2) {
		int tx = 2, ty = 1;
		g2.drawImage(gif1, 0, 0, 64, 64, tx * 64, ty * 64, 64 * tx + 64, 64 * ty + 64, this);
	}
	
	public void drawHappy(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		g2.fillArc(0, 0, 250, 250, 0, 360);
		g2.setColor(Color.BLACK);
		g2.fillArc(50, 50, 20, 20, 0, 360);
		g2.fillArc(200 - 20, 50, 20, 20, 0, 360);
		g2.fillArc(125 - 75, 50, 150, 150, 0, -180);
	}
	
	public void drawSad(Graphics2D g2) {
		g2.setColor(Color.blue);
		g2.fillArc(0, 0, 250, 250, 0, 360);
		g2.setColor(Color.BLACK);
		g2.fillArc(50, 50, 20, 20, 0, 360);
		g2.fillArc(200 - 20, 50, 20, 20, 0, 360);
		g2.setStroke(new BasicStroke(3));
		g2.drawArc(125 - 75, 125, 150, 150, 0, 180);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == happy) { 
			currState = State.HAPPY;
		}
		if (e.getSource() == sad) {
			currState = State.SAD;
		}
		if (e.getSource() == gif) {
			currState = State.GIF;
		}
		if (e.getSource() == doom) {
			initDoom();

		}
		repaint();
	}
	

	private void initPanels() {
		jp1 = new JPanel();
		happy = new JButton("Smile");
		sad = new JButton("Sad");
		gif = new JButton("Gif");
		doom = new JButton("...?");
		happy.addActionListener(this);
		sad.addActionListener(this);
		gif.addActionListener(this);
		doom.addActionListener(this);
		jp1.setBackground(Color.green);
		jp1.add(happy);
		jp1.add(sad);
		jp1.add(gif);
		jp1.add(doom);
		this.setLayout(bl);
		this.add(jp1, BorderLayout.SOUTH);
	}

	// custom textures		
//			 for(int x = 0; x < texWidth; x++) {
//				  for(int y = 0; y < texHeight; y++)
//				  {
//				    int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
//				    //int xcolor = x * 256 / texWidth;
//				    int ycolor = y * 256 / texHeight;
//				    int xycolor = y * 128 / texHeight + x * 128 / texWidth;
//				    boolean h = (x != y && x != texWidth - y);
//				    int i = 0;
//				    if (h) i = 1;
//				    texture[0][texWidth * y + x] = 65536 * 254 * i; //flat red texture with black cross
//				    texture[0][texWidth * y + x] = 65536 * 254 * 1; //flat red texture with black cross
//				    texture[1][texWidth * y + x] = xycolor + 256 * xycolor + 65536 * xycolor; //sloped greyscale
//				    texture[2][texWidth * y + x] = 256 * xycolor + 65536 * xycolor; //sloped yellow gradient
//				    texture[3][texWidth * y + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor; //xor greyscale

//				    texture[4][texWidth * y + x] = 256 * xorcolor; //xor green
//				    h = (x % 16 == 0 && y % 16 == 0);
//				    i = 0;
//				    if (h) i = 1;
//				    texture[5][texWidth * y + x] = 65536 * 192 * i; //red bricks
//				    texture[5][texWidth * y + x] = 65536 * 192 * 1; //red bricks
//				    texture[6][texWidth * y + x] = 65536 * ycolor; //red gradient
//				    texture[7][texWidth * y + x] = 128 + 256 * 128 + 65536 * 128; //flat grey texture
//				  }
//			 }
	///////////////////////////////////////////////////// REAL PROGRAM HERE LOLE //////////////////////////////////////////////////////////
	public KeyHandler keyH = new KeyHandler();
	MouseHandler mouseH = new MouseHandler(this);
	public Player player = new Player(this);

	public final int mapWidth = 24;
	public final int mapHeight = 24;
	public final int tileWidth = WIDTH / 10;
	public final int tileHeight = HEIGHT / 10;
	public final int FPS = 60;
	public boolean running = false;
	int map[][] = {
			  {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,7,7,7,7,7,7,7,7},
			  {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,7},
			  {4,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7},
			  {4,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7},
			  {4,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,7},
			  {4,0,4,0,0,0,0,5,5,5,5,5,5,5,5,5,7,7,0,7,7,7,7,7},
			  {4,0,5,0,0,0,0,5,0,5,0,5,0,5,0,5,7,0,0,0,7,7,7,1},
			  {4,0,6,0,0,0,0,5,0,0,0,0,0,0,0,5,7,0,0,0,0,0,0,8},
			  {4,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,7,1},
			  {4,0,8,0,0,0,0,5,0,0,0,0,0,0,0,5,7,0,0,0,0,0,0,8},
			  {4,0,0,0,0,0,0,5,0,0,0,0,0,0,0,5,7,0,0,0,7,7,7,1},
			  {4,0,0,0,0,0,0,5,5,5,5,0,5,5,5,5,7,7,7,7,7,7,7,1},
			  {6,6,6,6,6,6,6,6,6,6,6,0,6,6,6,6,6,6,6,6,6,6,6,6},
			  {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
			  {6,6,6,6,6,6,0,6,6,6,6,0,6,6,6,6,6,6,6,6,6,6,6,6},
			  {4,4,4,4,4,4,0,4,4,4,6,0,6,2,2,2,2,2,2,2,3,3,3,3},
			  {4,0,0,0,0,0,0,0,0,4,6,0,6,2,0,0,0,0,0,2,0,0,0,2},
			  {4,0,0,0,0,0,0,0,0,0,0,0,6,2,0,0,5,0,0,2,0,0,0,2},
			  {4,0,0,0,0,0,0,0,0,4,6,0,6,2,0,0,0,0,0,2,2,0,2,2},
			  {4,0,6,0,6,0,0,0,0,4,6,0,0,0,0,0,5,0,0,0,0,0,0,2},
			  {4,0,0,5,0,0,0,0,0,4,6,0,6,2,0,0,0,0,0,2,2,0,2,2},
			  {4,0,6,0,6,0,0,0,0,4,6,0,6,2,0,0,5,0,0,2,0,0,0,2},
			  {4,0,0,0,0,0,0,0,0,4,6,0,6,2,0,0,0,0,0,2,0,0,0,2},
			  {4,4,4,4,4,4,4,4,4,4,1,1,1,2,2,2,2,2,2,3,3,3,3,3}
	     };
	class Sprite {
		double x;
		double y;
		int texture;
		public Sprite(double x, double y, int texture) {
			this.x = x;
			this.y = y;
			this.texture = texture;
		}
	}
	// two > one plz
	static int randInt(int one, int two) {
		return (int) Math.round(Math.random() * (two - one)) + one;
	}
	
	class Enemy extends Sprite {
		public int hp = 4;
		public Vector2D dir = new Vector2D(0, 1);
		public boolean seesPlayer = false;
		Animation deathAnim = new Animation(4, 500, 13, 14, 15, 16);
		int deathTimer = 0;
		enum State {
			WALKING
		}
		State state = State.WALKING;
		public Enemy(double x, double y, int texture) {
			super(x, y, texture);
		}
		
		public void update() {
			
			if (hp <= 0) {
				int currTime = (int) System.nanoTime() / 1000000;
				if (currTime - deathTimer > deathAnim.delay && deathAnim.textureIndex < deathAnim.textures.length) {
					texture = deathAnim.textures[textureIndex];
					deathTimer = (int) System.nanoTime() / 1000000;
					deathAnim.textureIndex += 1;
				}
			}
		}
		
		public void initDeath() {
			deathTimer = (int) System.nanoTime() / 1000000;
		}
		
	}
	
	class Animation {
		public int[] textures;
		public int delay;
		public int textureIndex = 0;
		public Animation(int numTextures, int delay, int ...args) {
			this.textures = new int[numTextures];
			this.delay = delay;
			for(int i = 0; i < numTextures; i++) {
				textures[i] = args[i];
			}
		}
	}

	Sprite sprite[] =
		{
		  new Sprite(20.5, 11.5, 10),
		  
		  new Sprite(18.5, 4.5, 10),
		  new Sprite(10.0,4.5, 10),
		  new Sprite(10.0,12.5,10),
		  new Sprite(3.5, 6.5, 10),
		  new Sprite(3.5, 20.5,9),
		  new Sprite(3.5, 14.5,9),
		  new Sprite(14.5,20.5,9),
		  
		  new Sprite(18.5, 10.5, 9),
		  new Sprite(18.5, 11.5, 9),
		  new Sprite(18.5, 12.5, 8),
		  
		  new Sprite(21.5, 1.5, 8),
		  new Sprite(15.5, 1.5, 10),
		  new Sprite(16.0, 1.8, 10),
		  
		  new Sprite(17, 1.2, 9),
		  new Sprite(4,  2.5, 8),
		  new Sprite(10, 15.5, 8),
		  new Sprite(10.0, 15.1,9),
		  new Sprite(10.5, 15.8,10),
		  
		  new Enemy(21.5, 11.5, 11),
		  new Enemy(19.5, 4.5, 11),
		  new Enemy(5.5, 11.5, 11),
		  new Enemy(17.5,11.5,11),
		  new Enemy(21.5,7.5,11),
		  new Enemy(10.5,12.5,11),
		  new Enemy(7.5,8.5,11),
		  new Enemy(20.5,9.5,11),
		  new Enemy(13.5,20.5,11),
		  new Enemy(14.5,18.5,11),
		  new Enemy(1.5,1.5,11),
		};
	// 1D ZBuffer
	double[] ZBuffer = new double[WIDTH];
	
	// arrays used to sort the sprites
	public final int numSprites = 30;
	public final int numEnemies = 4;
	
	int[] spriteOrder = new int[numSprites];
	double[] spriteDistance = new double[numSprites];
	
	int[] enemyOrder = new int[numEnemies];
	double[] enemyDistance = new double[numEnemies];
	
	// use a function to sort the sprites;
	final double w = WIDTH;
	final double h = HEIGHT;
	final int texWidth = 64;
	final int texHeight = 64;
	int[] buffer = new int[WIDTH * HEIGHT * 3];
	int[] stupidBuffer = new int[WIDTH * HEIGHT * 3];
	int[][] texture = new int[numSprites][texWidth * texHeight];
	
	BufferedImage realBuffer = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
	WritableRaster raster = (WritableRaster) realBuffer.getData();
	BufferedImage gun;
	Robot robot;
	URL urlSound = null;
	
	final int numSounds = 6;
	URL[] imageUrl = new URL[numSprites];
	BufferedImage[] image = new BufferedImage[numSprites];
	public int textureIndex = 0;
	
	URL[] soundUrl = new URL[numSounds];
	AudioInputStream[] soundsIn = new AudioInputStream[numSounds];
	Clip[] clip = new Clip[numSounds];
	
	public int soundIndex = 0;
	
	
	public void loadTexture(String url) {
		try {
			imageUrl[textureIndex] = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		if (imageUrl[textureIndex] != null) {
			try {
				image[textureIndex] = ImageIO.read(imageUrl[textureIndex]);
			} catch (IOException e2){
				e2.printStackTrace();
			}
		}
		textureIndex += 1;
	}
	
	public void loadSound(String url) {
		try {
			soundUrl[soundIndex] = new URL(url);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		try {
			clip[soundIndex] = AudioSystem.getClip();
			soundsIn[soundIndex] = AudioSystem.getAudioInputStream(soundUrl[soundIndex]);
			clip[soundIndex].open(soundsIn[soundIndex]);
		} catch (UnsupportedAudioFileException | IOException e2) {
			e2.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		soundIndex += 1;
	}

	public void setTexture(int texNum, int x, int y, int width, int height, int texOffsetX, int texOffsetY, BufferedImage image) {
		int tx = x * texOffsetX;
		int ty = y * texOffsetY;
		for (int i = 0; i < texWidth; i++) {
			for (int j = 0; j < texHeight; j++) {
				int index = i * texHeight + j;
				texture[texNum][index] = image.getSubimage(tx, ty, width, height).getRGB(j, i);
			}
		}
	}
	
	private void initDoom()  {

		this.remove(jp1);
		
		loadSound("https://dl.sndup.net/82t7/Doom%20Pistol%20Sound%20Effect.wav");
		loadTexture("https://www.textures-resource.com/resources/sheets/2/1375.png?updated=1460970129"); // wall textures ----> 0
		loadTexture("https://www.spriters-resource.com/resources/sheets/25/27846.png?updated=1460955811"); // enemy textures -> 1
		loadTexture("https://i.ibb.co/1JZJhFZ/wolf-Atlas.png"); // Neutral sprites and guns  ---------------------------------> 2
		//https://www.spriters-resource.com/resources/sheets/99/102294.png?updated=1515879849
		//https://i.ibb.co/9Yrcd08/wolf-Atlas.png
		
		gun = image[2].getSubimage(196, 0, 64, 64);
		// WALLS
		// index, x, y, width, height, offsetX, offsetY, which texturepack to use from
		setTexture(0, 0, 0, 64, 64, 64, 64, image[0]);
		setTexture(1, 1, 0, 64, 64, 64, 64, image[0]);
		setTexture(2, 0, 2, 64, 64, 64, 64, image[0]);
		setTexture(3, 4, 3, 64, 64, 64, 64, image[0]);
		setTexture(4, 0, 4, 64, 64, 64, 64, image[0]);
		setTexture(5, 3, 8, 64, 64, 64, 64, image[0]);
		setTexture(6, 3, 5, 64, 64, 64, 64, image[0]);
		setTexture(7, 0, 0, 64, 64, 64, 64, image[0]);
		
		// SPRITE OBJECTS
		setTexture(8, 1, 0, 64, 64, 64, 64, image[2]);
		setTexture(9, 0, 0, 64, 64, 64, 64, image[2]);
		setTexture(10, 2, 0, 64, 64, 64, 64, image[2]);
		
		// ENEMIES
		setTexture(11, 0, 0, 64, 64, 64, 64, image[1]);
		setTexture(12, 1, 1, 64, 64, 65, 65, image[1]);
		setTexture(18, 0, 7, 64, 64, 64, 64, image[2]); // <-- USE THIS AS THE EMTPY SPRITE!!
		
		try {
			robot = new Robot();
		} catch (AWTException e3) {
			e3.printStackTrace();
		}
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		this.setCursor(blankCursor);
		addKeyListener(keyH);
		addMouseMotionListener(mouseH);
		addMouseListener(mouseH);
		currState = State.DOOM;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void update() {
		player.update();
		// ENEMY AI STUFF//////////////////////////////
		for(int i = 0; i < sprite.length; i++) {
			if (sprite[i] instanceof Enemy) {
				Enemy e = (Enemy) sprite[i];
				if (e.hp > 0) {
					if (e.seesPlayer) {
						e.texture = 11;
					} else {
						e.texture = 12;
					}
				
					int mapX = (int) Math.floor(e.x);
					int mapY = (int) Math.floor(e.y);
					int nextX = (int) (e.x + e.dir.x/2);
					int nextY = (int) (e.y + e.dir.y/2);
	
					if (map[nextX][nextY] == 0) {
						e.x += e.dir.x * 0.01;
						e.y += e.dir.y * 0.01;
					} else {
						int oldDirX = (int) e.dir.x;
						int oldDirY = (int) e.dir.y;
						if (randInt(0, 1) == 0) {
							e.dir.x = Math.ceil(oldDirX * Math.cos(Math.PI/2) - oldDirY * Math.sin(Math.PI/2));
							e.dir.y = Math.ceil(oldDirX * Math.sin(Math.PI/2) + oldDirY * Math.cos(Math.PI/2));
						} else {
							e.dir.x = Math.ceil(oldDirX * Math.cos(-Math.PI/2) - oldDirY * Math.sin(-Math.PI/2));
							e.dir.y = Math.ceil(oldDirX * Math.sin(-Math.PI/2) + oldDirY * Math.cos(-Math.PI/2));
						}
					}
				}
			}
		}
		// ENEMY SEEING STUFF ///////////////////////////////
		for (int i = 0; i < sprite.length; i++) {
			if (sprite[i] instanceof Enemy) {
				Enemy e = (Enemy) sprite[i];
				double diffPX = player.pos.x-sprite[i].x;
				double diffPY = player.pos.y-sprite[i].y;
				Vector2D dirPlayer = new Vector2D(diffPX, diffPY);
				diffPX = dirPlayer.getNormalized().x;
				diffPY = dirPlayer.getNormalized().y;
				
				int mapX = (int) Math.floor(sprite[i].x);
				int mapY = (int) Math.floor(sprite[i].y);

				double sideDistX = 0;
				double sideDistY = 0;

				double deltaDistX = Math.abs(1 / diffPX);
				double deltaDistY = Math.abs(1 / diffPY);

				int stepX = 1;
				int stepY = 1;
				
				int hit = 0;
				
				if (diffPX < 0) {
					stepX = -1;
					sideDistX = (sprite[i].x - mapX) * deltaDistX;
				} else {
					stepX = 1;
					sideDistX = (mapX + 1.0 - sprite[i].x) * deltaDistX;
				}
				if (diffPY < 0) {
					stepY = -1;
					sideDistY = (sprite[i].y - mapY) * deltaDistY;
				} else {
					stepY = 1;
					sideDistY = (mapY + 1.0 - sprite[i].y) * deltaDistY;
				}
				//perform DDA
				while (hit == 0) {
					if (sideDistX < sideDistY) {
						sideDistX += deltaDistX;
						mapX += stepX;
					} else {
						sideDistY += deltaDistY;
						mapY += stepY;
					}
					if (map[mapX][mapY] > 0) {
						hit = 1;
					}
					if (mapX == Math.floor(player.pos.x) && mapY == Math.floor(player.pos.y)) {
						hit = 2;
					}
				}
				if (hit == 1) {
					e.seesPlayer = false;
				}
				if (hit == 2) {
					e.seesPlayer = true;
				}
			}
		}
		
		
	}
	
	public void render(Graphics2D g2) {
		g2.setColor(new Color(250, 250, 250));
		g2.fillRect(0, 0, 800, 600);
		
		// FLOOR CASTING//////////////////////////////////////////////////////////////////// CEILING AND FLOOR//////////////////////////////////////////////
		for (int y = HEIGHT / 2 + 1; y < HEIGHT; ++y) {
			// rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
			float rayDirX0 = (float)(player.dir.x - player.plane.x);
			float rayDirY0 = (float)(player.dir.y - player.plane.y);
			float rayDirX1 = (float)(player.dir.x + player.plane.x);
			float rayDirY1 = (float)(player.dir.y + player.plane.y);
			
			// Current y position compared to the center of the screen (the horizon)
			int p = y - HEIGHT / 2;
		      // NOTE: with 0.5, it's exactly in the center between floor and ceiling,
		      // matching also how the walls are being raycasted. For different values
		      // than 0.5, a separate loop must be done for ceiling and floor since
		      // they're no longer symmetrical.
			float posZ = (float)0.5 * HEIGHT;
		     // Horizontal distance from the camera to the floor for the current row.
		      // 0.5 is the z position exactly in the middle between floor and ceiling.
		      // NOTE: this is affine texture mapping, which is not perspective correct
		      // except for perfectly horizontal and vertical surfaces like the floor.
		      // NOTE: this formula is explained as follows: The camera ray goes through
		      // the following two points: the camera itself, which is at a certain
		      // height (posZ), and a point in front of the camera (through an imagined
		      // vertical plane containing the screen pixels) with horizontal distance
		      // 1 from the camera, and vertical position p lower than posZ (posZ - p). When going
		      // through that point, the line has vertically traveled by p units and
		      // horizontally by 1 unit. To hit the floor, it instead needs to travel by
		      // posZ units. It will travel the same ratio horizontally. The ratio was
		      // 1 / p for going through the camera plane, so to go posZ times farther
		      // to reach the floor, we get that the total horizontal distance is posZ / p.
			float rowDistance = posZ / p;
			
			// Calculate the real world step vector we have to add for each x (parallel to camera plane)
			// adding step by step avoids multiplications with a weight in the inner loop
			float floorStepX = rowDistance * (rayDirX1 - rayDirX0) / WIDTH;
			float floorStepY = rowDistance * (rayDirY1 - rayDirY0) / WIDTH;
			
			// real world coordinates of the leftmost column. This will be updated as we step to the right.
			float floorX = (float)(player.pos.x + rowDistance * rayDirX0);
			float floorY = (float)(player.pos.y + rowDistance * rayDirY0);
			
			for (int x = 0; x < WIDTH; ++x) {
				// the cell coord is simply gotten from the itneger parts of floorX and floorY
				int cellX = (int)(floorX);
				int cellY = (int)(floorY);
				
				// get the texture coordinate from the fractional part;
				int tx = (int)(texWidth * (floorX - cellX)) & (texWidth - 1);
				int ty = (int)(texHeight * (floorY - cellY)) & (texHeight - 1);
				
				floorX += floorStepX;
				floorY += floorStepY;
				
				// choose the texture and draw the pixel
				int checkerBoardPattern = ((int)(cellX + cellY)) & 1;
				int floorTexture;
				if (checkerBoardPattern == 0) floorTexture = 3;
				else floorTexture = 4;
				int ceilingTexture = 6;
				int color;
				
				// floor
				color = texture[floorTexture][texWidth * ty + tx];
				color = (color >> 1) & 8355711; // make it a bit darker
				int index = (int)(y * WIDTH + x) * 3;
				buffer[index] = color / 65565;
				buffer[index + 1] = color / 256;
				buffer[index + 2] = color;
				
				// ceiling (symmetrical, at screen height - y - 1 instead of y)
				color = texture[ceilingTexture][texWidth * ty + tx];
				color = (color >> 1) & 8355711; // make it a bit darker
				// holy fuck im so smart save this shit for later
				// equivalent to buffer[HEIGHT - y - 1][x] with that index calculation
					index = (int)((HEIGHT - y - 1) * WIDTH + x) * 3;
					buffer[index] = color / 65565;
					buffer[index+1] = color / 256;
					buffer[index+2] = color;

				
				//
			}
		}
		// WALLS //////////////////////////////////////////////////////////////////// WALLS//////////////////////////////////////////////
		for (int x = 0; x < w; x+=1) {
			double cameraX = 2 * x / (double)w - 1;
			double rayDirX = player.dir.x + player.plane.x * cameraX;
			double rayDirY = player.dir.y + player.plane.y * cameraX;
			
			// which box of the map we're in
			int mapX = (int) Math.floor(player.pos.x);
			int mapY = (int) Math.floor(player.pos.y);
			
			// length of ray from current position to next x or y-side
			double sideDistX = 0;
			double sideDistY = 0;
			
			// length of ray from one x or y-side to next x or y-side
//			double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
//			double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);
			double deltaDistX = Math.abs(1 / rayDirX);
			double deltaDistY = Math.abs(1 / rayDirY);
			double perpWallDist = 0;
			
			// what direction to step in x or y-direction (either +1 or -1)
			int stepX = 1;
			int stepY = 1;
			
			int hit = 0; // was there a wall hit?
			int side = 0;    // was a NS or a EW wall hit? 
			
			// calculate step and initial sideDist
			if (rayDirX < 0) {
				stepX = -1;
				sideDistX = (player.pos.x - mapX) * deltaDistX;
			} else {
				stepX = 1;
				sideDistX = (mapX + 1.0 - player.pos.x) * deltaDistX;
			}
			if (rayDirY < 0) {
				stepY = -1;
				sideDistY = (player.pos.y - mapY) * deltaDistY;
			} else {
				stepY = 1;
				sideDistY = (mapY + 1.0 - player.pos.y) * deltaDistY;
			}
			//perform DDA
			while (hit == 0) {
				// jump to next square, either in x-direction or in y-direction
				if (sideDistX < sideDistY) {
					sideDistX += deltaDistX;
					mapX += stepX;
					side = 0;
				} else {
					sideDistY += deltaDistY;
					mapY += stepY;
					side = 1;
				}
				// check if ray has hit a wall
				if (map[mapX][mapY] > 0) hit = 1;
			}
			
			// Calculate distance projected on camera direction
			if (side == 0)
				perpWallDist = (sideDistX - deltaDistX);
			else		   
				perpWallDist = (sideDistY - deltaDistY);
			// Calculate height of line to draw on screen
			int lineHeight = (int)(h / perpWallDist);
			int pitch = 0;
			// Calculate lowest and highest pixel to fill in current stripe
			int drawStart = -lineHeight / 2 + (int)h / 2 + pitch;
			if (drawStart < 0) drawStart = 0;
			int drawEnd = lineHeight / 2 + (int)h / 2 + pitch;
			if (drawEnd >= h) drawEnd = (int)h - 1;
//			
//			//////////////////////// texturing
			int texNum = map[mapX][mapY] - 1; // texture 0 is at 1, texture 1 is at 2, etc so 0 is empty
			// calculate value of wallX
			double wallX;
			if (side == 0) { // where exactly the wall was hit
				wallX = player.pos.y + perpWallDist * rayDirY;
			} else {
				wallX = player.pos.x + perpWallDist * rayDirX;
			}
			wallX -= Math.floor((wallX));
//			
//			// x coordinate on the texture
			int texX = (int)(wallX * (double)(texWidth));
			if (side == 0 && rayDirX > 0) texX = texWidth - texX - 1;
			if (side == 1 && rayDirY < 0) texX = texWidth - texX - 1;
//			
//			// How much to increase the texture coordinate per screen pixel
			double step = 1.0 * texHeight / lineHeight;
			// Starting texture coordinate
			double texPos = (drawStart - pitch - h / 2 + lineHeight / 2) * step;
			for (int y = drawStart; y < drawEnd; y++) {
//				// Cast the texture coordinate to integer, and mask with (texHeight - 1) in case of overflow
				int texY = (int)texPos & (texHeight - 1);
				texPos += step;
				int color = texture[texNum][texHeight * texY + texX];
//				// make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
				if (side == 1) color = (color >> 1) & 8355711;
//				
//				realBuffer.setRGB(x, y, color);
				if (!keyH.fkey) {
					int index = (int)((y * WIDTH + x) * 3);
					buffer[index] = color / 65565;
					buffer[index+1] = color / 256;
					buffer[index+2] = color;
				}

			}
			// SET THE ZBUFFER FOR THE SPRITE CASTING
			ZBuffer[x] = perpWallDist;
		}
		// SPRITES //////////////////////////////////////////////////////////////////// SPRITES//////////////////////////////////////////////
		// SPRITE CASTING
		// sort sprites from far to close
		for (int i = 0; i < numSprites; i++) {
			spriteOrder[i] = i;
			spriteDistance[i] = ((player.pos.x - sprite[i].x) * (player.pos.x - sprite[i].x) + (player.pos.y - sprite[i].y) * (player.pos.y - sprite[i].y));
		}
		
		// SORT SPRITES HERE TODO MAKE A O(nlogn) SORT FUNCTION INSTEAD 
		for (int i = 0; i < spriteDistance.length - 1; i++) {
			for (int j = i; j < spriteDistance.length; j++) {
				if (spriteDistance[j] < spriteDistance[i]) {
					double temp = spriteDistance[j];
					spriteDistance[j] = spriteDistance[i];
					spriteDistance[i] = temp;
					
					int temp2 = spriteOrder[j];
					spriteOrder[j] = spriteOrder[i];
					spriteOrder[i] = temp2;
				}
			}
		}
		
		for (int i = 0; i < (Math.floor(spriteDistance.length / 2)); i++) {
			double temp = spriteDistance[i];
			spriteDistance[i] = spriteDistance[spriteDistance.length - 1 - i];
			spriteDistance[spriteDistance.length - 1 - i] = temp;
			
			int temp2 = spriteOrder[i];
			spriteOrder[i] = spriteOrder[spriteOrder.length - 1 - i];
			spriteOrder[spriteOrder.length - 1 - i] = temp2;
		}
		// after sorting the sprites, do the projection and draw them
		for (int i = 0; i < numSprites; i++) {
			// translate sprite position relative to camera
			double spriteX = sprite[spriteOrder[i]].x - player.pos.x;
			double spriteY = sprite[spriteOrder[i]].y - player.pos.y;
		      //transform sprite with the inverse camera matrix
		      // [ planeX   dirX ] -1                                       [ dirY      -dirX ]
		      // [               ]       =  1/(planeX*dirY-dirX*planeY) *   [                 ]
		      // [ planeY   dirY ]                                          [ -planeY  planeX ]

			double invDet = 1.0 / (player.plane.x * player.dir.y - player.plane.y * player.dir.x);
			double transformX = invDet * (player.dir.y * spriteX - player.dir.x * spriteY);
			double transformY = invDet * (-player.plane.y * spriteX + player.plane.x * spriteY);
			
			int spriteScreenX = (int)((WIDTH / 2) * (1 + transformX / transformY));
			
			int uDiv = 1;
			int vDiv = 1;
			double vMove = 0.0;
			int vMoveScreen = (int)(vMove / transformY);
			
			//calculate height of sprite on screen
			int spriteHeight = Math.abs((int)(HEIGHT / transformY)) / vDiv;
			// calculate lowest and highest pixel to fill in current stripe
			int drawStartY = (int)(-spriteHeight / 2 + HEIGHT / 2 + vMoveScreen);
			if (drawStartY < 0) drawStartY = 0;
			int drawEndY = (int)(spriteHeight / 2 + HEIGHT / 2 + vMoveScreen);
			if (drawEndY >= HEIGHT) drawEndY = (int)(HEIGHT - 1);
			
			// calculate width of the sprite
			int spriteWidth = Math.abs((int)(HEIGHT / transformY)) / uDiv;
			int drawStartX = (int)(-spriteWidth / 2 + spriteScreenX);
			if (drawStartX < 0) drawStartX = 0;
			int drawEndX = (int)(spriteWidth / 2 + spriteScreenX);
			if (drawEndX >= WIDTH) drawEndX = (int)(WIDTH - 1);

			// loop through every vertical stripe of the sprite on screen
			for(int stripe = drawStartX; stripe < drawEndX; stripe++) {
				int texX = (int)(256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texWidth / spriteWidth) / 256;
				
				if ((transformY > 0 && transformY < ZBuffer[stripe]) || keyH.fkey) { // for every pixel of the current stripe
					for (int y = drawStartY; y < drawEndY; y++) {
						
						int d = (int)((y - vMoveScreen) * 256 - HEIGHT * 128 + spriteHeight * 128); // 256 and 128 factors to avoid floats
						int texY = (int)((d * texHeight) / spriteHeight) / 256;
						int	fullTexY = sprite[spriteOrder[i]].texture;
						int fullTexX = texWidth * texY + texX;
						int color = 0;
						if (fullTexX >= 0 && fullTexY >= 0) {
							color = texture[fullTexY][fullTexX]; // get current color from the texture
						}
						if ((color & 0x0067FF77) != 0) {
							int i2 = (y * WIDTH + stripe) * 3;
							buffer[i2] = color / 65565;
							buffer[i2+1] = color / 256;
							buffer[i2+2] = color;
						}
						if (spriteScreenX == stripe) {
							int i2 = (y * WIDTH + stripe) * 3;
							buffer[i2] = color;
							buffer[i2+1] = color;
							buffer[i2+2] = color;
						}
					}
				}
			}
		}
		raster.setPixels(0, 0, WIDTH, HEIGHT, buffer);
		realBuffer.setData(raster);
		g2.drawImage(realBuffer, 0, 0, this);
		int gW = 200;
		int gH = 200;
		g2.drawImage(gun, (int)(w / 2 - gW / 2), (int)(h - gH), 200, 200, this);
		
		for (int y = 0; y < h; y++) { 
			for (int x = 0; x < w; x++) {
				realBuffer.setRGB(x, y, 100);
				int index = (y * WIDTH + x) * 3;
				buffer[index] = 0;
				buffer[index+1] = 0;
				buffer[index+2] = 0;
			}
		}

		g2.setColor(Color.RED);
		g2.fillRect((int)w/2, 0, 2, (int)h);
		
		g2.setColor(Color.WHITE);
		double roundX = Math.round(player.pos.x * 100.0) / 100.0;
		double roundY = Math.round(player.pos.y * 100.0) / 100.0;
		g2.drawString("X: " + roundX, 10, 10);
		g2.drawString("Y: " + roundY, 10, 25);
		int mapX = (int)(Math.floor(player.pos.x));
		int mapY = (int)(Math.floor(player.pos.y));
		g2.drawString("MapXY: " + map[mapX][mapY], 10, 40);
	}
	
	FloatControl gainControl;
	float currDB = 0F;
	float targetDB = 0F;
	float fadePerStep = .1F;
	boolean fading = false;
	public void setVolume(double value) {
		value = (value <= 0.0)? 0.0001 : ((value > 1.0) ? 1.0 : value);
		try {
			float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void shiftVolumeTo(double value) {
		// value is between 0 and 1
		value = (value <= 0.0) ? 0.0001 : ((value > 1.0) ? 1.0 : value);
		targetDB = (float)(Math.log(value) / Math.log(10.0) * 20.0);
		if (!fading) {
			Thread t = new Thread(this);
			t.start();
		}
	} 
	
	class Player {

		public Vector2D pos = new Vector2D(22, 11.5);
		public Vector2D dir = new Vector2D(-1, 0); // initial direction vector
		public Vector2D plane = new Vector2D(0, 0.66); // the 2d ray-cast version of a camera plane
		public Vector2D vel = new Vector2D(0, 0);
		public int r = 10;
		public double moveSpeed = 0.05;
		public double rotSpeed = 0.002;
		public int lastTurnAmountX = 0;
		public int lastTurnAmountY = 0;
		int delay = 666;
		int bulletTimer = (int) (System.nanoTime() / 1000000);
		int shootTimer = (int) (System.nanoTime() / 1000000);
		int shootDelay = 450;
		boolean shooting = false;
		private App app;
		public Player(App app) {
			this.app = app;

		}
		public void update() {
			vel.set(0, 0);
			if (app.keyH.up) {
				if (app.map[(int)(pos.x + dir.x * moveSpeed)][(int)pos.y] == 0) pos.x += dir.x * moveSpeed;
				if (app.map[(int)(pos.x)][(int)(pos.y + dir.y * moveSpeed)] == 0) pos.y += dir.y * moveSpeed;
			}
			if (app.keyH.down) {
				if (app.map[(int)(pos.x - dir.x * moveSpeed)][(int)pos.y] == 0) pos.x -= dir.x * moveSpeed;
				if (app.map[(int)(pos.x)][(int)(pos.y - dir.y * moveSpeed)] == 0) pos.y -= dir.y * moveSpeed;
			}
			
			if (app.keyH.right) {
				if (app.map[(int)(pos.x + plane.x * moveSpeed)][(int)pos.y] == 0) pos.x += plane.x * moveSpeed;
				if (app.map[(int)(pos.x)][(int)(pos.y + plane.y * moveSpeed)] == 0) pos.y += plane.y * moveSpeed;
			}
			if (app.keyH.left) {
				if (app.map[(int)(pos.x - plane.x * moveSpeed)][(int)pos.y] == 0) pos.x -= plane.x * moveSpeed;
				if (app.map[(int)(pos.x)][(int)(pos.y - plane.y * moveSpeed)] == 0) pos.y -= plane.y * moveSpeed;
			}

			if (mouseH.turnAmountX > lastTurnAmountX) {
				int diff = mouseH.turnAmountX - lastTurnAmountX;
				for (int i = 0; i < diff; i++) {
					double oldDirX = dir.x;
					dir.x = dir.x * Math.cos(-rotSpeed) - dir.y * Math.sin(-rotSpeed);
					dir.y = oldDirX * Math.sin(-rotSpeed) + dir.y * Math.cos(-rotSpeed);
					double oldPlaneX = plane.x;
					plane.x = plane.x * Math.cos(-rotSpeed) - plane.y * Math.sin(-rotSpeed);
					plane.y = oldPlaneX * Math.sin(-rotSpeed) + plane.y * Math.cos(-rotSpeed);
				}
			}
			if (mouseH.turnAmountX < lastTurnAmountX) {
				int diff = lastTurnAmountX - mouseH.turnAmountX;
				for (int i = 0; i < diff; i++) {
					double oldDirX = dir.x;
					dir.x = dir.x * Math.cos(rotSpeed) - dir.y * Math.sin(rotSpeed);
					dir.y = oldDirX * Math.sin(rotSpeed) + dir.y * Math.cos(rotSpeed);
					double oldPlaneX = plane.x;
					plane.x = plane.x * Math.cos(rotSpeed) - plane.y * Math.sin(rotSpeed);
					plane.y = oldPlaneX * Math.sin(rotSpeed) + plane.y * Math.cos(rotSpeed);
				}
			}
			mouseH.turnAmountX = lastTurnAmountX;
			
			if (mouseH.clicking) {
				int diff = (int) (System.nanoTime() / 1000000 - bulletTimer);
				if (diff > delay) {
					bulletTimer = (int) (System.nanoTime() / 1000000);
					shootTimer = (int) (System.nanoTime() / 1000000);
					shooting = true;
					clip[0].stop();
					clip[0].flush();


					clip[0].setFramePosition(0);
					clip[0].start();
					int x = (int) (w / 2);
					double cameraX = 2 * x / (double)w - 1;
					double rayDirX = dir.x + plane.x * cameraX;
					double rayDirY = dir.y + plane.y * cameraX;
					
					int mapX = (int) Math.floor(pos.x);
					int mapY = (int) Math.floor(pos.y);
					
					double sideDistX = 0;
					double sideDistY = 0;
					
					double deltaDistX = Math.abs(1 / rayDirX);
					double deltaDistY = Math.abs(1 / rayDirY);
					double perpWallDist = 0;
					
					int stepX = 1;
					int stepY = 1;
					
					int hit = 0; // was there a wall hit?
					int side = 0;    
					
					if (rayDirX < 0) {
						stepX = -1;
						sideDistX = (player.pos.x - mapX) * deltaDistX;
					} else {
						stepX = 1;
						sideDistX = (mapX + 1.0 - player.pos.x) * deltaDistX;
					}
					if (rayDirY < 0) {
						stepY = -1;
						sideDistY = (player.pos.y - mapY) * deltaDistY;
					} else {
						stepY = 1;
						sideDistY = (mapY + 1.0 - player.pos.y) * deltaDistY;
					}
					
					//perform DDA
					while (hit == 0) {
						// jump to next square, either in x-direction or in y-direction
						if (sideDistX < sideDistY) {
							sideDistX += deltaDistX;
							mapX += stepX;
							side = 0;
						} else {
							sideDistY += deltaDistY;
							mapY += stepY;
							side = 1;
						}
						// check if ray has hit a wall
						if (map[mapX][mapY] > 0) hit = 1;
					}
					

					if (side == 0)
						perpWallDist = (sideDistX - deltaDistX);
					else		   
						perpWallDist = (sideDistY - deltaDistY);

					double wallX;
					if (side == 0) { // where exactly the wall was hit
						wallX = player.pos.y + perpWallDist * rayDirY;
					} else {
						wallX = player.pos.x + perpWallDist * rayDirX;
					}
					
					wallX -= Math.floor((wallX));

//					System.out.println(perpWallDist);
					
					
					for (int i = 0; i < numSprites; i++) {
						spriteOrder[i] = i;
						spriteDistance[i] = ((pos.x - sprite[i].x) * (pos.x - sprite[i].x) + (pos.y - sprite[i].y) * (pos.y - sprite[i].y));
					}
					
					for (int i = 0; i < spriteDistance.length - 1; i++) {
						for (int j = i; j < spriteDistance.length; j++) {
							if (spriteDistance[j] < spriteDistance[i]) {
								double temp = spriteDistance[j];
								spriteDistance[j] = spriteDistance[i];
								spriteDistance[i] = temp;
								
								int temp2 = spriteOrder[j];
								spriteOrder[j] = spriteOrder[i];
								spriteOrder[i] = temp2;
							}
						}
					}
					
					for (int i = 0; i < (Math.floor(spriteDistance.length / 2)); i++) {
						double temp = spriteDistance[i];
						spriteDistance[i] = spriteDistance[spriteDistance.length - 1 - i];
						spriteDistance[spriteDistance.length - 1 - i] = temp;
						
						int temp2 = spriteOrder[i];
						spriteOrder[i] = spriteOrder[spriteOrder.length - 1 - i];
						spriteOrder[spriteOrder.length - 1 - i] = temp2;
					}
					
					for (int i = 0; i < numSprites; i++) {
						double spriteX = sprite[spriteOrder[i]].x - pos.x;
						double spriteY = sprite[spriteOrder[i]].y - pos.y;

						double invDet = 1.0 / (plane.x * dir.y - plane.y * dir.x);
						double transformX = invDet * (dir.y * spriteX - dir.x * spriteY);
						double transformY = invDet * (-plane.y * spriteX + plane.x * spriteY);
//						System.out.println(transformY);
						int spriteScreenX = (int)((WIDTH / 2) * (1 + transformX / transformY));
						// loop through every vertical stripe of the sprite on screen
						int checkX = (int) (w / 2);

						// calculate width of the sprite
						int spriteWidth = Math.abs((int)(HEIGHT / transformY));
						int drawStartX = (int)(-spriteWidth / 2 + spriteScreenX);
						if (drawStartX < 0) drawStartX = 0;
						int drawEndX = (int)(spriteWidth / 2 + spriteScreenX);
						if (drawEndX >= WIDTH) drawEndX = (int)(WIDTH - 1);
						int realWidth = drawEndX - drawStartX;
//						System.out.println(spriteWidth);
						
						
						x = (int) (w / 2);
						rayDirX = dir.x + plane.x * cameraX;
						rayDirY = dir.y + plane.y * cameraX;
						
						mapX = (int) Math.floor(pos.x);
						mapY = (int) Math.floor(pos.y);
						
						sideDistX = 0;
						sideDistY = 0;
						
						deltaDistX = Math.abs(1 / rayDirX);
						deltaDistY = Math.abs(1 / rayDirY);
						double enemyDist = 0;
						
						stepX = 1;
						stepY = 1;
						
						hit = 0; // was there a wall hit?
						side = 0;    
						
						if (rayDirX < 0) {
							stepX = -1;
							sideDistX = (player.pos.x - mapX) * deltaDistX;
						} else {
							stepX = 1;
							sideDistX = (mapX + 1.0 - player.pos.x) * deltaDistX;
						}
						if (rayDirY < 0) {
							stepY = -1;
							sideDistY = (player.pos.y - mapY) * deltaDistY;
						} else {
							stepY = 1;
							sideDistY = (mapY + 1.0 - player.pos.y) * deltaDistY;
						}
						
						//perform DDA
						while (hit == 0) {
							// jump to next square, either in x-direction or in y-direction
							if (sideDistX < sideDistY) {
								sideDistX += deltaDistX;
								mapX += stepX;
								side = 0;
							} else {
								sideDistY += deltaDistY;
								mapY += stepY;
								side = 1;
							}
							if (mapY > 24 || mapX > 24 || mapX < 0 || mapY < 0) break;
							// check if ray has hit an enemy
							if (Math.floor(sprite[spriteOrder[i]].x) == mapX && Math.floor(sprite[spriteOrder[i]].y) == mapY) hit = 1;
						}
						

						if (side == 0)
							enemyDist = (sideDistX - deltaDistX);
						else		   
							enemyDist = (sideDistY - deltaDistY);
						
						
						
						
						
						
						
						enemyDist = Math.sqrt(spriteX * spriteX + spriteY * spriteY);
						
						
						if (transformY > 0 && transformY < ZBuffer[checkX] && sprite[spriteOrder[i]] instanceof Enemy) { 
							if (spriteScreenX - realWidth / 4 <= w/2 && spriteScreenX + realWidth / 4 >= w/2) {
								if (enemyDist < perpWallDist) {
									Enemy e = (Enemy)sprite[spriteOrder[i]];
									e.hp -= 1;
									System.out.println("HIT");
									
									if (e.hp <= 0) {
										System.out.println("hp" + e.hp);
										e.texture = 18;
										e.initDeath();
									}

								}
							}
						}
					}
					
					
					

					
					
					
					gun = image[2].getSubimage(256, 0, 64, 64);
				}
			}
			
			if (shooting) {
				int diff = (int) (System.nanoTime() / 1000000 - shootTimer);
				if (diff > shootDelay) {
					shootTimer = (int) (System.nanoTime() / 1000000);
					shooting = false;
					gun = image[2].getSubimage(196, 0, 64, 64);
				}
			}
			pos.add(vel);
		}
		public void draw(Graphics2D g2) {
			g2.setColor(Color.yellow);
			g2.fillArc((int)pos.x, (int)pos.y, r, r, 0, 360);
			g2.setColor(Color.black);
			
		}
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = drawInterval + System.nanoTime();
		while(running) {
			
			update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000;
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	class KeyHandler implements KeyListener {
		public boolean up = false;
		public boolean down = false;
		public boolean left = false;
		public boolean right = false;
		public boolean fkey = false;
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			handleKeys(code, true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			handleKeys(code, false);
		}

		private void handleKeys(int code, boolean isPressed) {
			if(code == KeyEvent.VK_W) {
				up = isPressed;
			}
			if(code == KeyEvent.VK_S) {
				down = isPressed;
			}
			if(code == KeyEvent.VK_A) {
				left = isPressed;
			}
			if(code == KeyEvent.VK_D) {
				right = isPressed;
			}
			if (code == KeyEvent.VK_F) {
				fkey = isPressed;
			}
		}
	}
	
	class MouseHandler extends MouseAdapter {

		public int mouseX, mouseY;
		public boolean clicking = false;
		public int turnAmountX = 0;
		public int turnAmountY = 0;
		App app;
		public MouseHandler(App app) {
			this.app = app;
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			turnAmountX += e.getX() - WIDTH / 2;
			turnAmountY += e.getY() - HEIGHT / 2;
			robot.mouseMove(app.getLocationOnScreen().x + WIDTH / 2, app.getLocationOnScreen().y + HEIGHT / 2);
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			turnAmountX += e.getX() - WIDTH / 2;
			turnAmountY += e.getY() - HEIGHT / 2;
			robot.mouseMove(app.getLocationOnScreen().x + WIDTH / 2, app.getLocationOnScreen().y + HEIGHT / 2);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			clicking = true;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			clicking = false;
		}
	}

}


//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NO REASON TO GO BELOW HERE LOL /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


class Vector2D {

    public double x;
    public double y;
    
    public Vector2D() { }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        set(v);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setZero() {
        x = 0;
        y = 0;
    }

    public double[] getComponents() {
        return new double[]{x, y};
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getLengthSq() {
        return (x * x + y * y);
    }

    public double distanceSq(double vx, double vy) {
        vx -= x;
        vy -= y;
        return (vx * vx + vy * vy);
    }

    public double distanceSq(Vector2D v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return (vx * vx + vy * vy);
    }

    public double distance(double vx, double vy) {
        vx -= x;
        vy -= y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double distance(Vector2D v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void normalize() {
        double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }

    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    public static Vector2D toCartesian(double magnitude, double angle) {
        return new Vector2D(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(double vx, double vy) {
        this.x += vx;
        this.y += vy;
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public Vector2D getAdded(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public void subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void subtract(double vx, double vy) {
        this.x -= vx;
        this.y -= vy;
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public Vector2D getSubtracted(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public Vector2D getMultiplied(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public void divide(double scalar) {
        x /= scalar;
        y /= scalar;
    }

    public Vector2D getDivided(double scalar) {
        return new Vector2D(x / scalar, y / scalar);
    }

    public Vector2D getPerp() {
        return new Vector2D(-y, x);
    }

    public double dot(Vector2D v) {
        return (this.x * v.x + this.y * v.y);
    }

    public double dot(double vx, double vy) {
        return (this.x * vx + this.y * vy);
    }

    public static double dot(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public double cross(Vector2D v) {
        return (this.x * v.y - this.y * v.x);
    }

    public double cross(double vx, double vy) {
        return (this.x * vy - this.y * vx);
    }

    public static double cross(Vector2D v1, Vector2D v2) {
        return (v1.x * v2.y - v1.y * v2.x);
    }

    public double project(Vector2D v) {
        return (this.dot(v) / this.getLength());
    }

    public double project(double vx, double vy) {
        return (this.dot(vx, vy) / this.getLength());
    }

    public static double project(Vector2D v1, Vector2D v2) {
        return (dot(v1, v2) / v1.getLength());
    }

    public Vector2D getProjectedVector(Vector2D v) {
        return this.getNormalized().getMultiplied(this.dot(v) / this.getLength());
    }

    public Vector2D getProjectedVector(double vx, double vy) {
        return this.getNormalized().getMultiplied(this.dot(vx, vy) / this.getLength());
    }

    public static Vector2D getProjectedVector(Vector2D v1, Vector2D v2) {
        return v1.getNormalized().getMultiplied(Vector2D.dot(v1, v2) / v1.getLength());
    }

    public void rotateBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double rx = x * cos - y * sin;
        y = x * sin + y * cos;
        x = rx;
    }

    public Vector2D getRotatedBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    public void rotateTo(double angle) {
        set(toCartesian(getLength(), angle));
    }

    public Vector2D getRotatedTo(double angle) {
        return toCartesian(getLength(), angle);
    }

    public void reverse() {
        x = -x;
        y = -y;
    }

    public Vector2D getReversed() {
        return new Vector2D(-x, -y);
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector2D) {
            Vector2D v = (Vector2D) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector2d[" + x + ", " + y + "]";
    }
}