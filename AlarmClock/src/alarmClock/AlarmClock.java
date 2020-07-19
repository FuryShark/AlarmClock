package alarmClock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class AlarmClock extends JFrame implements Observer {
	
	private final Font font = new Font("SansSerif", Font.PLAIN, 20);

	private LinkedList<Long> queue = new LinkedList<Long>();
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private Model model;
	
	public LinkedList<Long> getQueue(){
		return queue;
	}

	public AlarmClock(Model m) {
		this.model = m;
		initComponents();
		dlm.addElement("No alarms set");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static String timeFormatHMS(long time) {
		StringBuilder t = new StringBuilder();
		long total_secs = time / 1000L;
		long total_mins = total_secs / 60L;
		long total_hrs = total_mins / 60L;
		int secs = (int) total_secs % 60;
		int mins = (int) total_mins % 60;
		int hrs = (int) total_hrs % 24;
		if (hrs < 10) {
			t.append("0");
		}
		t.append(hrs).append(":");
		if (mins < 10) {
			t.append("0");
		}
		t.append(mins).append(":");
		if (secs < 10) {
			t.append("0");
		}
		t.append(secs);
		return t.toString();
	}

	private void buttonAddActionPerformed(ActionEvent e) {
		int hour = (int) spinnerHour.getValue();
		int minute = (int) spinnerMinute.getValue();
		int second = (int) spinnerSecond.getValue();
		Long alarmTimeMs = (long) ((hour * 3600000) + (minute * 60000) + (second * 1000));
		addToQueue(alarmTimeMs);
		dlm.clear();
		for (int i = 0; i < queue.size(); i++) {
			dlm.addElement("Alarm set: " + timeFormatHMS(queue.get(i)));
		}
		listAlarms.setModel(dlm);
	}


	private void addToQueue(Long alarmTimeMs) {
		if (queue.isEmpty()) {
			queue.add(alarmTimeMs);
		} else if (queue.get(0) > alarmTimeMs) {
			queue.add(0, alarmTimeMs);
		} else if (queue.get(queue.size() - 1) < alarmTimeMs) {
			queue.add(queue.size(), alarmTimeMs);
		} else {
			int i = 0;
			while (queue.get(i) < alarmTimeMs) {
				i++;
			}
			queue.add(i, alarmTimeMs);
		}
	}

	private void buttonRemoveActionPerformed(ActionEvent e) {
		if (dlm.size() != -1 ) {
			if (listAlarms.getSelectedIndex() != -1) {
				String selection = listAlarms.getSelectedValue().split("set: ")[1];
				if (selection.equals("No alarms set")) {
					return;
				} else {
					int hour = Integer.parseInt(selection.split(":")[0]);
					int minute = Integer.parseInt(selection.split(":")[1]);
					int second = Integer.parseInt(selection.split(":")[2]);
					Long alarmTimeMs = (long) ((hour * 3600000) + (minute * 60000) + (second * 1000));
					queue.remove(alarmTimeMs);
					dlm.remove(listAlarms.getSelectedIndex());
				}
			} else {
				String selection = dlm.get(dlm.getSize() - 1);
				if (selection.equals("No alarms set")) {
					return;
				} else {
					
					dlm.remove(dlm.getSize() - 1);
					
				}
			}
			if (dlm.isEmpty()) {
				dlm.addElement("No alarms set");
			}
		}
		return;
	}
	
	public JPanel getPanelClock() {
		return panelClock;
	}
	



	private void initComponents() {
		panelClock = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Paint code from Clock-master
				Rectangle bounds = getBounds();
				Graphics2D gg = (Graphics2D) g;
				int x0 = bounds.width / 2;
				int y0 = bounds.height / 2;
				int size = Math.min(x0, y0);
				gg.setStroke(new BasicStroke(1));
				double radius = 0;
				double theta = 0;
				for (int n = 0; n < 60; n++) {
					theta = (90 - n * 6) / (180 / Math.PI);
					if (n % 5 == 0) {
						radius = 0.65 * size;
					} else {
						radius = 0.7 * size;
					}
					double x1 = x0 + radius * Math.cos(theta);
					double y1 = y0 - radius * Math.sin(theta);
					radius = 0.75 * size;
					double x2 = x0 + radius * Math.cos(theta);
					double y2 = y0 - radius * Math.sin(theta);
					gg.draw(new Line2D.Double(x1, y1, x2, y2));
				}
				gg.setFont(font);
				for (int n = 1; n <= 12; n++) {
					theta = (90 - n * 30) / (180 / Math.PI);
					radius = 0.9 * size;
					double x1 = x0 + radius * Math.cos(theta);
					double y1 = y0 - radius * Math.sin(theta);
					String s = "" + n;
					FontRenderContext context = gg.getFontRenderContext();
					Rectangle2D msgbounds = font.getStringBounds(s, context);
					double descent = msgbounds.getHeight() + msgbounds.getY();
					double height = msgbounds.getHeight();
					double width = msgbounds.getWidth();
					gg.drawString(s, (new Float(x1 - width / 2)).floatValue(),
							(new Float(y1 + height / 2 - descent)).floatValue());
				}
				gg.setStroke(new BasicStroke(2.0f));
				theta = (90 - (model.hour + model.minute / 60.0) * 30) / (180 / Math.PI);
				radius = 0.5 * size;
				double x1 = x0 + radius * Math.cos(theta);
				double y1 = y0 - radius * Math.sin(theta);
				gg.draw(new Line2D.Double(x0, y0, x1, y1));
				gg.setStroke(new BasicStroke(1.1f));
				theta = (90 - (model.minute + model.second / 60.0) * 6) / (180 / Math.PI);
				radius = 0.75 * size;
				x1 = x0 + radius * Math.cos(theta);
				y1 = y0 - radius * Math.sin(theta);
				gg.draw(new Line2D.Double(x0, y0, x1, y1));
				gg.setColor(Color.red);
				gg.setStroke(new BasicStroke(0));
				theta = (90 - model.second * 6) / (180 / Math.PI);
				x1 = x0 + radius * Math.cos(theta);
				y1 = y0 - radius * Math.sin(theta);
				gg.draw(new Line2D.Double(x0, y0, x1, y1));
			};
		};
		scrollPaneAlarms = new JScrollPane();
		listAlarms = new JList<>();
		spinnerHour = new JSpinner();
		labelHour = new JLabel();
		labelMinute = new JLabel();
		spinnerMinute = new JSpinner();
		labelSecond = new JLabel();
		spinnerSecond = new JSpinner();
		buttonAdd = new JButton();
		buttonRemove = new JButton();

		//======== this ========
		setTitle("AlarmClock");
		Container contentPane = getContentPane();

		//======== panelClock ========
		{
			panelClock.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

			GroupLayout panelClockLayout = new GroupLayout(panelClock);
			panelClock.setLayout(panelClockLayout);
			panelClockLayout.setHorizontalGroup(
					panelClockLayout.createParallelGroup()
					.addGap(0, 198, Short.MAX_VALUE)
					);
			panelClockLayout.setVerticalGroup(
					panelClockLayout.createParallelGroup()
					.addGap(0, 200, Short.MAX_VALUE)
					);
		}

		//======== scrollPaneAlarms ========
		{

			//---- listAlarms ----
			listAlarms.setModel(new AbstractListModel<String>() {
				String[] values = {
						"No alarms set"
				};
				@Override
				public int getSize() { return values.length; }
				@Override
				public String getElementAt(int i) { return values[i]; }
			});
			scrollPaneAlarms.setViewportView(listAlarms);
		}

		//---- spinnerHour ----
		spinnerHour.setModel(new SpinnerNumberModel(0, 0, 23, 1));

		//---- labelHour ----
		labelHour.setText("HOUR");
		labelHour.setHorizontalAlignment(SwingConstants.CENTER);

		//---- labelMinute ----
		labelMinute.setText("MINUTE");
		labelMinute.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerMinute ----
		spinnerMinute.setModel(new SpinnerNumberModel(0, 0, 59, 1));

		//---- labelSecond ----
		labelSecond.setText("SECOND");
		labelSecond.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerSecond ----
		spinnerSecond.setModel(new SpinnerNumberModel(0, 0, 59, 1));

		//---- buttonAdd ----
		buttonAdd.setText("ADD ALARM");
		buttonAdd.addActionListener(e -> buttonAddActionPerformed(e));

		//---- buttonRemove ----
		buttonRemove.setText("REMOVE ALARM");
		buttonRemove.addActionListener(e -> buttonRemoveActionPerformed(e));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(buttonAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPaneAlarms, GroupLayout.Alignment.TRAILING)
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(panelClock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createSequentialGroup()
										.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(spinnerHour, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
												.addComponent(labelHour, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(contentPaneLayout.createParallelGroup()
												.addComponent(spinnerMinute)
												.addComponent(labelMinute, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(spinnerSecond, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
												.addComponent(labelSecond, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
								.addComponent(buttonRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelClock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneAlarms, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(labelHour)
								.addComponent(labelSecond)
								.addComponent(labelMinute))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(spinnerSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerHour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(buttonAdd)
						.addGap(18, 18, 18)
						.addComponent(buttonRemove)
						.addContainerGap(13, Short.MAX_VALUE))
				);
		pack();
		setLocationRelativeTo(getOwner());
	}

	private JPanel panelClock;
	private JScrollPane scrollPaneAlarms;
	private JList<String> listAlarms;
	private JSpinner spinnerHour;
	private JLabel labelHour;
	private JLabel labelMinute;
	private JSpinner spinnerMinute;
	private JLabel labelSecond;
	private JSpinner spinnerSecond;
	private JButton buttonAdd;
	private JButton buttonRemove;

	@Override
	public void update(Observable o, Object arg) {
		panelClock.repaint();
	}
}
