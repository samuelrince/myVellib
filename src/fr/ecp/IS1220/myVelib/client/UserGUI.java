package fr.ecp.IS1220.myVelib.client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JOptionPane;
import fr.ecp.IS1220.myVelib.core.*;

public class UserGUI extends JFrame {
	private User user;
	int alertCount;
	boolean GPSauthor = false;
	
	private JPanel home = new JPanel();
	private JPanel alertPan = new JPanel();
	private JPanel planRidePan = new JPanel();
	private JPanel statPan = new JPanel();
	private JPanel subPan = new JPanel();
	private JPanel newRidePan = new JPanel();
	private Panneau planPanneau;
	
	private JButton backBtn1 = new JButton("back");
	private JButton backBtn2 = new JButton("back");
	private JButton backBtn3 = new JButton("back");
	private JButton backBtn4 = new JButton("back");
	private JButton backBtn5 = new JButton("back");
	private JButton alertBtn = new JButton("ALERTS: "+alertCount);
	private JButton planBtn = new JButton("PLAN OF STATIONS");
	private JButton planRideBtn = new JButton("PLAN A RIDE");
	private JButton statBtn = new JButton("MY STATISTICS");
	private JButton subBtn = new JButton("MY SUBSCRIPTION");
	private JButton discardBtn = new JButton("DISCARD");
	private JButton startBtn = new JButton("START");
	private JButton newRideBtn = new JButton("NEW RIDE");
	private JButton confirmRideBtn = new JButton("Confirm");
	private JRadioButton standardBtn = new JRadioButton("standard");
	private JRadioButton vlibreBtn = new JRadioButton("Vlibre");
	private JRadioButton vmaxBtn = new JRadioButton("Vmax");
	private ButtonGroup subtypeBtnGroup = new ButtonGroup();
	
	private JComboBox<String> bikeTypeComboBox = new JComboBox<String>();
	private JComboBox<String> pathStratComboBox = new JComboBox<String>();
	private JCheckBox myPositionCheckBox = new JCheckBox("From my position");
	private JCheckBox GPSauthorCheckBox = new JCheckBox("Share my position with MyVelibApp");
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu optionsMenu = new JMenu("Options");
	private JMenu bgColorMenu = new JMenu("Background theme");
	private JMenu prefMenu = new JMenu("Preferences");
	private JMenuItem passwordMenuItem = new JMenuItem("Change password");
	private JMenuItem fbMenuItem = new JMenuItem("Send feedback");
	private JMenuItem helpMenuItem = new JMenuItem("Help");
	private JMenuItem bgYellowMenuItem = new JMenuItem("Sun");
	private JMenuItem bgCyanMenuItem = new JMenuItem("Ocean");
	private JMenuItem bgGreyMenuItem = new JMenuItem("Metal");
	
	private JTextArea statTxt = new JTextArea();
	private JTextArea alertTxt = new JTextArea();
	private JTextArea travelTxt = new JTextArea();
	private JTextArea historyTxt = new JTextArea();
	private JScrollPane historyScrollPan = new JScrollPane(historyTxt);
	
	private JTextField sourceLatTxt = new JTextField(1);
	private JTextField sourceLongTxt = new JTextField(1);
	private JTextField destLatTxt = new JTextField(1);
	private JTextField destLongTxt = new JTextField(1);


	public UserGUI(User user){
		super("MyVelibApp v.1.0 - welcome"+user.getName()+"!");
		this.user = user;

		this.setSize(500, 300);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		int margin = 30;
		int height = (int)this.getSize().getHeight();
    	int width = (int)this.getSize().getWidth();
    	int length = Math.min(height, width)-margin;
    	ArrayList<Localization> points = new ArrayList<Localization>();
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<Station> stationDatabase = MyVelibNetwork.getInstance().getStationDatabase();
		for (Station s:stationDatabase) {
			points.add(s.getLocalization());
			labels.add(s.getName());
		}
		planPanneau = new Panneau(points,labels,length,margin);
		
		this.alertCount = user.msgBox.getUncheckedCount();
		alertBtn.setText(("ALERTS: "+alertCount));
		if (this.alertCount == 0)
			alertBtn.setEnabled(false);

		home.setBackground(Color.CYAN);  
		alertPan.setBackground(Color.CYAN);  
		statPan.setBackground(Color.CYAN);  
		subPan.setBackground(Color.CYAN);  
		planRidePan.setBackground(Color.CYAN);  
		newRidePan.setBackground(Color.CYAN); 
		home.setLayout(new BorderLayout(3,3));
		historyScrollPan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		alertBtn.addActionListener(new alertBtnListener());
		planBtn.addActionListener(new planBtnListener());
		planRideBtn.addActionListener(new planRideBtnListener());
		startBtn.addActionListener(new startBtnListener());
		discardBtn.addActionListener(new discardBtnListener());
		newRideBtn.addActionListener(new newRideBtnListener());
		confirmRideBtn.addActionListener(new confirmRideBtnListener());
		myPositionCheckBox.addActionListener(new myPositionCheckBoxListener());
		statBtn.addActionListener(new statBtnListener());
		subBtn.addActionListener(new subBtnListener());
		backBtn1.addActionListener(new backBtnListener());
		backBtn2.addActionListener(new backBtnListener());
		backBtn3.addActionListener(new backBtnListener());
		backBtn4.addActionListener(new backBtnListener());
		backBtn5.addActionListener(new backBtnListener());
		standardBtn.addActionListener(new subtypeBtnListener());
		vlibreBtn.addActionListener(new subtypeBtnListener());
		vmaxBtn.addActionListener(new subtypeBtnListener());
		bgYellowMenuItem.addActionListener(new bgColorMenuItemListener());
		bgCyanMenuItem.addActionListener(new bgColorMenuItemListener());
		bgGreyMenuItem.addActionListener(new bgColorMenuItemListener());
		fbMenuItem.addActionListener(new fbMenuItemListener());
		GPSauthorCheckBox.addActionListener(new GPSauthorActionListener());
		
		home.add(alertBtn, BorderLayout.NORTH);
		home.add(planBtn, BorderLayout.SOUTH);
		home.add(planRideBtn, BorderLayout.CENTER);
		home.add(statBtn, BorderLayout.EAST);
		home.add(subBtn, BorderLayout.WEST);
		planRidePan.add(backBtn1);
		statPan.add(backBtn2);
		subPan.add(backBtn3);
		alertPan.add(backBtn4);
		planPanneau.add(backBtn5);
		planRidePan.add(newRideBtn);
		discardBtn.setBackground(Color.RED);
		planRidePan.add(discardBtn);
		startBtn.setBackground(Color.GREEN);
		planRidePan.add(startBtn);
		subtypeBtnGroup.add(standardBtn);
		subtypeBtnGroup.add(vlibreBtn);
		subtypeBtnGroup.add(vmaxBtn);
		subPan.add(standardBtn);
		subPan.add(vlibreBtn);
		subPan.add(vmaxBtn);
		newRidePan.add(confirmRideBtn);
		
		bgColorMenu.add(bgYellowMenuItem);
		bgColorMenu.add(bgCyanMenuItem);
		bgColorMenu.add(bgGreyMenuItem);
		prefMenu.add(bgColorMenu);
		prefMenu.add(GPSauthorCheckBox);
		optionsMenu.add(passwordMenuItem);
		optionsMenu.add(prefMenu);
		optionsMenu.add(fbMenuItem);
		optionsMenu.add(helpMenuItem);
		menuBar.add(optionsMenu);
		
		alertTxt.setEditable(false);
		alertPan.add(alertTxt);
		statTxt.setEditable(false);
		statPan.add(statTxt);
		travelTxt.setEditable(false);
		planRidePan.add(travelTxt);
		sourceLatTxt.setBorder(BorderFactory.createLineBorder(Color.black));
		newRidePan.add(sourceLatTxt);
		sourceLongTxt.setBorder(BorderFactory.createLineBorder(Color.black));
		newRidePan.add(sourceLongTxt);
		destLatTxt.setBorder(BorderFactory.createLineBorder(Color.black));
		newRidePan.add(destLatTxt);
		destLongTxt.setBorder(BorderFactory.createLineBorder(Color.black));
		newRidePan.add(destLongTxt);
		historyTxt.setEditable(false);
		
		bikeTypeComboBox.addItem("-bicycle type-");
		bikeTypeComboBox.addItem("Mechanical");
		bikeTypeComboBox.addItem("Electrical");
		newRidePan.add(bikeTypeComboBox);
		pathStratComboBox.addItem("-path strategy-");
		pathStratComboBox.addItem("Minimal walking");
		pathStratComboBox.addItem("Fastest path");
		pathStratComboBox.addItem("Prefer Plus");
		pathStratComboBox.addItem("Avoid Plus");
		pathStratComboBox.addItem("Preserve distribution");
		newRidePan.add(pathStratComboBox);
		newRidePan.add(myPositionCheckBox);
		
		newRidePan.setVisible(false);
		planRidePan.add(newRidePan);
		historyScrollPan.setVisible(true);
		statPan.add(historyScrollPan);
		planPanneau.setVisible(true);
		
		this.setJMenuBar(menuBar);
		this.setContentPane(home);
		
	}
	
	class alertBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	String alerts = user.getMsgBox().retrieve();
	    	alertTxt.setText(alerts);
	    	
	    	UserGUI.this.setContentPane(alertPan);
	    	UserGUI.this.setVisible(true);
	    }
	  }
	
	class planBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
			UserGUI.this.setVisible(false);
			UserGUI.this.setContentPane(planPanneau);
			UserGUI.this.setVisible(true);
	    }
	  }
	
	class planRideBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	if (user.getPlannedRide() != null) {
	    		travelTxt.setText(user.getPlannedRide().toString());
	    		discardBtn.setEnabled(true);
	    		if(!user.getPlannedRide().isOngoing())
	    			startBtn.setEnabled(true);
	    	}
	    	else {
	    		travelTxt.setText("No ride planned for the moment.");
	    		startBtn.setEnabled(false);
	    		discardBtn.setEnabled(false);
	    	}
	    	UserGUI.this.setContentPane(planRidePan);
	    	UserGUI.this.setVisible(true);
	    	
	    }
	  }
	
	class newRideBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int option;
			sourceLatTxt.setBorder(BorderFactory.createLineBorder(Color.black));
			sourceLongTxt.setBorder(BorderFactory.createLineBorder(Color.black));
			destLatTxt.setBorder(BorderFactory.createLineBorder(Color.black));
			destLongTxt.setBorder(BorderFactory.createLineBorder(Color.black));
			sourceLatTxt.setText("");
			sourceLongTxt.setText("");
			destLatTxt.setText("");
			destLongTxt.setText("");
			bikeTypeComboBox.setSelectedIndex(0);
			pathStratComboBox.setSelectedIndex(0);
			if (user.getPlannedRide() != null ) {
				option = JOptionPane.showConfirmDialog(null, 
						"You are about to discard your current planned ride. Confirm?", 
						"Discard previous planned ride", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.YES_OPTION){
					user.discardPlannedRide();
					travelTxt.setText("No ride planned for the moment.");
					newRidePan.setVisible(true);
				}
			}
			else
				newRidePan.setVisible(true);


		}
	}

	class startBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			user.getPlannedRide().start();
			startBtn.setText("STARTED");
			startBtn.setEnabled(false);
		}
	}
	
	class discardBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			user.discardPlannedRide();
			travelTxt.setText("No ride planned for the moment.");
			startBtn.setText("START");
			discardBtn.setEnabled(false);
			startBtn.setEnabled(false);
		}
	}
	
	class confirmRideBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean validCoord = true;
			try {
				Double.parseDouble(sourceLatTxt.getText());
				sourceLatTxt.setBorder(BorderFactory.createLineBorder(Color.green));
			}
			catch (Exception ex) {sourceLatTxt.setBorder(BorderFactory.createLineBorder(Color.red));validCoord = false;}
			try {
				Double.parseDouble(sourceLongTxt.getText());
				sourceLongTxt.setBorder(BorderFactory.createLineBorder(Color.green));
			}
			catch (Exception ex) {sourceLongTxt.setBorder(BorderFactory.createLineBorder(Color.red));validCoord = false;}
			try {
				Double.parseDouble(destLatTxt.getText());
				destLatTxt.setBorder(BorderFactory.createLineBorder(Color.green));
			}
			catch (Exception ex) {destLatTxt.setBorder(BorderFactory.createLineBorder(Color.red));validCoord = false;}
			try {
				Double.parseDouble(destLongTxt.getText());
				destLongTxt.setBorder(BorderFactory.createLineBorder(Color.green));
			}
			catch (Exception ex) {destLongTxt.setBorder(BorderFactory.createLineBorder(Color.red));validCoord = false;}
			if (!validCoord)
				return;
			Localization source = new Localization(Double.parseDouble(sourceLatTxt.getText()),
					Double.parseDouble(sourceLongTxt.getText()));
			Localization destination = new Localization(Double.parseDouble(destLatTxt.getText()),
					Double.parseDouble(destLongTxt.getText()));
			String bicycleType = (String) bikeTypeComboBox.getSelectedItem();
			if (bicycleType.equalsIgnoreCase("-bicycle type-"))
					bicycleType = null;			
			String pathStrategy = (String) pathStratComboBox.getSelectedItem();
			if (pathStrategy.equalsIgnoreCase("-path strategy-"))
					pathStrategy = "minimal walking";
			try {
			PathStrategyFactory stratFactory = new PathStrategyFactory();
			PathStrategy pathStrat = stratFactory.newPathStrategy(pathStrategy);
			user.planRide(source, destination, bicycleType, pathStrat);
			travelTxt.setText(user.getPlannedRide().toString());
			newRidePan.setVisible(false);
			startBtn.setEnabled(true);
			discardBtn.setEnabled(true);
			}
			catch (Exception ex) {JOptionPane.showMessageDialog(null, "Sorry, we couldn't find a ride with these parameters.", 
					"Failure", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class myPositionCheckBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (myPositionCheckBox.isSelected()) {
				if (!GPSauthor) {
					int option;
					option = JOptionPane.showConfirmDialog(null, 
							"This action requires access to your position. Do you accept to"
									+ " share your position with MyVelibApp?", 
									"GPS authorisation", 
									JOptionPane.YES_NO_OPTION, 
									JOptionPane.WARNING_MESSAGE);
					if(option == JOptionPane.YES_OPTION) {
						GPSauthor = true;
						GPSauthorCheckBox.setSelected(true);
					}
					else {
						myPositionCheckBox.setSelected(false);
						return;
					}
				}
				if (user.getLocalization() != null) {
					sourceLatTxt.setText(Double.toString(user.getLocalization().getLatitude()));
					sourceLongTxt.setText(Double.toString(user.getLocalization().getLongitude()));
					sourceLatTxt.setEnabled(false);
					sourceLongTxt.setEnabled(false);

				}
				else {
					JOptionPane.showMessageDialog(null, "Sorry, your position was not found.", 
							"Failure", JOptionPane.ERROR_MESSAGE);
					myPositionCheckBox.setSelected(false);
				}
			}
			else {
				sourceLatTxt.setEnabled(true);
				sourceLongTxt.setEnabled(true);
			}
		}
	}

	class statBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	statTxt.setText(UserBalance.representation(UserGUI.this.user));
	    	historyTxt.setText(user.getHistory());
	    	UserGUI.this.setContentPane(statPan);
	    	UserGUI.this.setVisible(true);
	    }
	  }
	
	class subBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	String subtype = user.getCard().getType();
	    	if (subtype.equalsIgnoreCase("standard"))
	    		standardBtn.setSelected(true);
	    	if (subtype.equalsIgnoreCase("vlibre"))
	    		vlibreBtn.setSelected(true);
	    	if (subtype.equalsIgnoreCase("vmax"))
	    		vmaxBtn.setSelected(true);
	    	UserGUI.this.setContentPane(subPan);
	    	UserGUI.this.setVisible(true);
	    }
	  }
	
	class subtypeBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			 int option;
			 String subtype = null;
			 if (e.getSource() == standardBtn)
				 subtype = "standard";
			 if (e.getSource() == vlibreBtn)
				 subtype = "vlibre";
			 if (e.getSource() == vmaxBtn)
				 subtype = "vmax";
			 if (subtype.equalsIgnoreCase(user.getCard().getType()))
				 return;
			 option = JOptionPane.showConfirmDialog(null, 
					 "Do you really want to make your subscription '"+subtype+"'?", 
					 "Change of subcription", 
					 JOptionPane.YES_NO_OPTION, 
					 JOptionPane.QUESTION_MESSAGE);
			 if(option == JOptionPane.YES_OPTION){
				 user.subscribe(subtype);
				 JOptionPane.showMessageDialog(null, "Operation confirmed", "", JOptionPane.INFORMATION_MESSAGE);
			 }
			 else {
				 if (user.getCard().getType().equalsIgnoreCase("standard"))
					 standardBtn.setSelected(true);
				 if (user.getCard().getType().equalsIgnoreCase("vlibre"))
					 vlibreBtn.setSelected(true);
				 if (user.getCard().getType().equalsIgnoreCase("vmax"))
					 vmaxBtn.setSelected(true);
			 }
		}
	}
	
	class backBtnListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	alertCount = user.msgBox.getUncheckedCount();
	    	alertBtn.setText("ALERTS: "+alertCount);
	    	if (alertCount == 0)
				alertBtn.setEnabled(false);
	    	UserGUI.this.setContentPane(home);
	    	UserGUI.this.setVisible(true);
	    }
	  }
	
	class GPSauthorActionListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) { 
	    	if (GPSauthorCheckBox.isSelected())
	    		GPSauthor = true;
	    	else
	    		GPSauthor = false;
	    }
	}
	
	class bgColorMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Color color = null;
			if (e.getSource() == bgYellowMenuItem)
				color = Color.YELLOW;
			if (e.getSource() == bgCyanMenuItem)
				color = Color.CYAN;
			if (e.getSource() == bgGreyMenuItem)
				color = Color.LIGHT_GRAY;
			home.setBackground(color);  
			alertPan.setBackground(color);  
			statPan.setBackground(color);  
			subPan.setBackground(color);  
			planRidePan.setBackground(color);  
			newRidePan.setBackground(color); 

		}
	}
	
	class fbMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageIcon img = new ImageIcon("heart.png");
			String[] grade = {"20"};
			JOptionPane.showInputDialog(null,user.getName()+", how would you rate MyVelibApp out of 20?",
					"Feedback",JOptionPane.QUESTION_MESSAGE,null,grade,grade[0]);
			 JOptionPane.showMessageDialog(null, "Thank you for your feedback!", "Thank you", JOptionPane.INFORMATION_MESSAGE,img);
		}
	}


	public static void main(final String[] args) throws Exception{
		SystemDate SD = SystemDate.getInstance();
		SD.setDay(2019, 02, 17);SD.setTime(0, 0, 0);
		MyVelibNetwork network = new MyVelibNetwork("Paris");
		network.createStations(new Localization(48.86101631231847,2.33583927154541), 5., 10, 4, 10, 70., new double[] {70,30});
		network.createSubscribers(10,"vlibre");
		User user = network.user(0);
		user.notifyUser("Notification 1");
		user.notifyUser("Notification 2");
		user.setLocalisation(new Localization(48.86101631231847,2.33583927154541));
		user.planRide(new Localization(48,2), new Localization(48,3),"electrical");
		SwingUtilities.invokeLater (new Runnable (){public void run () {new UserGUI(user);}});
	}

}
	

