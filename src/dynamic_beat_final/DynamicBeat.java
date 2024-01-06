package dynamic_beat_final;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {

	// 더블 버퍼링 기술 사용
	private Image screenImage;
	private Graphics screenGraphic;
	
	// 이미지를 가져오기 위함.
	private ImageIcon exitButtonEnteredImage = new ImageIcon ("images/exitButtonEntered.png");
	private ImageIcon exitButtonBasicImage = new ImageIcon ("images/exitButtonBasic.png");
	private ImageIcon startButtonEnteredImage = new ImageIcon ("images/startButtonEntered.png");
	private ImageIcon startButtonBasicImage = new ImageIcon ("images/startButtonBasic.png");
	private ImageIcon quitButtonEnteredImage = new ImageIcon ("images/quitButtonEntered.png");
	private ImageIcon quitButtonBasicImage = new ImageIcon ("images/quitButtonBasic.png");
	private ImageIcon leftButtonEnteredImage = new ImageIcon ("images/leftButtonEntered.png");
	private ImageIcon leftButtonBasicImage = new ImageIcon ("images/leftButtonBasic.png");
	private ImageIcon rightButtonEnteredImage = new ImageIcon ("images/rightButtonEntered.png");
	private ImageIcon rightButtonBasicImage = new ImageIcon ("images/rightButtonBasic.png");
	private ImageIcon easyButtonEnteredImage = new ImageIcon ("images/easyButtonEntered.png");
	private ImageIcon easyButtonBasicImage = new ImageIcon ("images/easyButtonBasic.png");
	private ImageIcon hardButtonEnteredImage = new ImageIcon ("images/hardButtonEntered.png");
	private ImageIcon hardButtonBasicImage = new ImageIcon ("images/hardButtonBasic.png");
	private ImageIcon backButtonEnteredImage = new ImageIcon ("images/backButtonEntered.png");
	private ImageIcon backButtonBasicImage = new ImageIcon ("images/backButtonBasic.png");
	
	// 백그라운드 이미지
	private Image background = new ImageIcon ("images/introBackground(Title).jpg").getImage();
	
	// 메뉴바 이미지
	private JLabel menuBar = new JLabel (new ImageIcon ("images/menuBar.png"));
	
	// Button 생성
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton backButton = new JButton(backButtonBasicImage);
	
	// 윈도우 창 위치를 메뉴바를 끌어서 옮길 수 있도록 마우스 좌표 int
	private int mouseX, mouseY;

	// 게임에 맞춰 화면을 표시하기 위한 변수
	private boolean isMainScreen = false;
	// Ingame 으로 넘어왔는지 확인하기 위한 변수수
	private boolean isGameScreen = false;
	
	// ArrayList 어떠한 변수를 담을 수 있는 이미 만들어진 배열? => 하나의 음악의 정보를 배열로 담음
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	// trackList 안에 있는 값에 따라서 아래 변수들의 값이 달라짐
    // nowSelected 값은 현재 선택된 트랙의 번호이자 arraylist 의 index 를 의미.
	private Image titleImage;
	private Image selectedImage;
	private Music selectedMusic;
	private Music introMusic = new Music("introMusic.mp3", true); // 인트로 음악 정의
	private int nowSelected = 0;
	
	private boolean isResult = false;
	ScoreResult scoreResult;
	    
	// Game 인스턴스 생성 && 초기화 :
    // 이때 game 변수는 단 하나의 게임만 진행가능하며 game 변수자체가 프로젝트 전체에서 사용되어야하기 때문에
    // static 으로 만들어줌
	public static Game game;
	
	public DynamicBeat() {
		
		trackList.add(new Track("Easy Love Title Image.png", "Easy Love Start Image.png", "Easy Love Game Image.png",
		        "Easy Love Selected.mp3", "Easy Love - Hotham.mp3", "Easy Love - Hotham", 198000));
		trackList.add(new Track("Feel Me Title Image.png", "Feel Me Start Image.png", "Feel Me Game Image.png",
				"Feel Me Selected.mp3", "Feel Me - Rexlambo.mp3", "Feel Me - Rexlambo", 223000));
		trackList.add(new Track("Oceans Title Image.png", "Oceans Start Image.png", "Oceans Game Image.png",
				"Oceans Selected.mp3", "Oceans - Away Hotham.mp3", "Oceans - Away Hotham", 182000));
		
		setUndecorated(true); // 기본 메뉴바 삭제
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false); // 한번 창이 생성되면 임의적으로 창 크기 변경 불가
		setLocationRelativeTo(null); // 창이 정중앙에 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 게임 창을 종료 시 프로그램 전체 종료
		setVisible(true); // 창 보이는 여부
		setBackground(new Color(0, 0, 0, 0)); 
		setLayout(null); // 버튼이나 JLabel을 넣었을 때 그 위치 그대로 넣어짐
		
		// add해서 KeyListener 에 내가 만든 KeyListen 인식
		addKeyListener(new KeyListener());
		
		// 게임 시작시 인트로 음악 재생
		introMusic.start();
		
		// exitButton
		exitButton.setBounds(1245, 0, 30, 30); // x, y, 길이, 높이
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(750);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton);
		
		//startButton
		startButton.setBounds(40, 200, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				introMusic.close();
				// 게임 시작 이벤트
				enterMain();
			}
		});
		add(startButton);
		
		// quitButton
		quitButton.setBounds(40, 330, 400, 100);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton);
		
		// leftButton
		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 왼쪽 버튼 이벤트
				selectLeft();
			}
		});
		add(leftButton);
		
		// rightButton
		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 오른쪽 버튼 이벤트
				selectRight();
			}
		});
		add(rightButton);
		
		// easyButton
		easyButton.setVisible(false);
		easyButton.setBounds(375, 580, 250, 67);
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 난이도 쉬움 이벤트
				gameStart(nowSelected, "Easy");
			}
		});
		add(easyButton);
		
		// hardButton
		hardButton.setVisible(false);
		hardButton.setBounds(655, 580, 250, 67);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardButtonEnteredImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 난이도 어려움 이벤트
				gameStart(nowSelected, "Hard");
			}
		});
		add(hardButton);
		
		// backButton
		backButton.setVisible(false);
		backButton.setBounds(20, 50, 60, 60);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(backButtonEnteredImage);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(backButtonBasicImage);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 메인 화면으로 돌아가는 이벤트
				backMain();
			}
		});
		add(backButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 윈도우 화면 크기만큼 생성
		screenGraphic = screenImage.getGraphics(); // screenImage를 이용해서 그래픽 객체를 얻어옴
		screenDraw((Graphics2D) screenGraphic); // 그래픽스 2D로 형변환
		g.drawImage(screenImage, 0, 0, null); // 윈도우 창에 screenImage를 뿌려줌
	}

	public void screenDraw(Graphics2D g) { // 매개변수를 Graphics 에서 Graphics2D 로 변경
//      System.out.println("c");
      // drawImage 메서드를 Introbackground 를  x, y 좌표에 그려줌
      // g.drawImage 부분은 paintComponents 처럼 화면에 추가된 요소를 그려주는 것이 아닌 단순히
      // 이미지를 그림 그리는 것
		g.drawImage(background, 0, 0, null);
		
		// isMainScreen = true 면 selectedImage 를 보여줌
		if(isMainScreen) 
		{
			g.drawImage(selectedImage, 340, 100, null);
			g.drawImage(titleImage, 340, 70, null);
		}
		// isGameScreen = true 인게임 화면에서의 그래픽
        // ingame 에 관한 그래픽 내용은 Game 클래스에서 관리
		if(isGameScreen)
		{
			game.screenDraw(g);
		}
		// paintComponents 는 이미지를 단순히 그려주는 것 이외에 JLabel 처럼 추가된 요소를 그리는 것
        // 즉 JFrame 위에 button 이나 라벨처럼 add() 된 부분에 대한 것
		paintComponents(g);
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	// 현재 선택된 곡의 번호를 넣어줌으로써 해당 곡이 선택됨을 알림
	public void selectTrack(int nowSelected) {
		// 선택한 곡이 null 이 아니면 , 즉 어떠한 곡이라도 하나가 실행되고 있다면 해당 음악을 종료
		if(selectedMusic != null)
			selectedMusic.close();
		// 현재 선택된 곡이 갖고 있는 noewSelected 번호를 갖고 아래의 각 정보를 가져옴
	    // 예를 들어서 arraylist 의 index 가 1이면 1에 해당하는 title, start, music 를 가져와서 뿌려줌
		titleImage = new ImageIcon("images/" + trackList.get(nowSelected).getTitleImage()).getImage();
		selectedImage = new ImageIcon("images/" + trackList.get(nowSelected).getStartImage()).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}
	
	public void selectLeft() {
		// 0번째 곡일때는 전체 trackList 크기에서 -1 한다.
	    // 이는 0번째 곡일때 왼쪽을 누르면 track 에 있는 마지막 곡이 나오게 됨
		if(nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else // 가장 왼쪽 아닐때는 현재 nowSelected 에서 -1
			nowSelected--; 
		selectTrack(nowSelected);
	}
	
	public void selectRight() {
		// 현재 곡이 track 의 가장 오른쪽에, 즉 마지막에 있는 곡이라면
	    // 가장 처음으로 돌아가도록
		if(nowSelected == trackList.size() - 1)
			nowSelected = 0;
		else // 가장 오른쪽이 아닌 경우는 +1
			nowSelected++;
		selectTrack(nowSelected);
	}
	
	public void gameStart(int nowSelected, String difficulty) {
		if(selectedMusic != null) // 현재 재생되고 있는 음악이 있다면 음악 종료
			selectedMusic.close();
		isMainScreen = false; // 메인 스크린을 false 로 => 이렇게되면 screenDraw 함수에서 isMainScreen 부분을 멈추게됨
		//버튼 안보이게
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		
		// 백그라운드 이미지가 ingame 이미지로 바뀌어야함
		background = new ImageIcon ("images/" + trackList.get(nowSelected).getGameImage()).getImage();
		backButton.setVisible(true); // 뒤로 돌아가기 버튼
		
		// Ingame 전환 확인
		isGameScreen = true;
		
		// 게임 시작 시 해당 선택된 곡 이름과 난이도 가져옴
		game = new Game(trackList.get(nowSelected).getTitleName(), difficulty, trackList.get(nowSelected).getGameMusic());
		
		// game 인스턴스 안에 있는 run 함수 실행
		game.start();
		
		// 게임 시작 시 점수 & 콤보 초기화
		Game.combo = 0;
	    Game.score = 0;

	    // 키보드 이벤트 동작을 위한 메서드
	    // 이는 Main 클래스에 포커스가 맞춰져있어야 키보드 이벤트가 정상적으로 동작하기 때문
		setFocusable(true);
		requestFocus();
	}
	
	public void backMain() {
		// 메인 화면일때는 isMainScreen 이 true, GameScreen 은 false
		isMainScreen = true;
		isGameScreen = false;
		isResult = false;
        if(!isResult && scoreResult != null){
            scoreResult.close();
        }
		// 버튼 보이게
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		// 백그라운드 이미지가 track 의 nowSelected 에 맞는 이미지로
		background = new ImageIcon ("images/mainBackground.jpg").getImage();
		// 뒤로 돌아가기 버튼
		backButton.setVisible(false);
		// 다시 트랙 선택
		selectTrack(nowSelected);
		// 메뉴로 돌아왔을 때 게임 종료
		game.close();
	}
	
	public void enterMain() {
		// 시작, 종료버튼 없애기
		startButton.setVisible(false);
		quitButton.setVisible(false);
		// 게임 메인 화면에 들어갔을 때 배경화면
		background = new ImageIcon ("images/mainBackground.jpg").getImage();
		// 게임 시작을 누르면 isMainScreen 을 true 로
		isMainScreen = true;
		// 버튼 보이기
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		// 인트로 음악 종료
		introMusic.close();
		// nowselected 번째 index 재생
		selectTrack(0);
		// 음악 종료 후 스코어 결과를 표시하는 코드
	    ScoreResult scoreResult = new ScoreResult((Graphics2D) getGraphics());
	    scoreResult.start();
	}

}
