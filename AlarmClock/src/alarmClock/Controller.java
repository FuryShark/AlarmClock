package alarmClock;

import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Controller {

	private ActionListener listener;
	private Timer timer;

	private Model model;
	private AlarmClock alarmClock;
	
	/**
	 * Repaints the Clock face every second to update the time
	 * Displays a dialog box message if current time is past next alarm time
	 * 
	 * @param m Model instance
	 * @param c AlarmClock instance
	 */
	public Controller(Model m, AlarmClock c) {
		model = m;
		alarmClock = c;

		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				model.update();

				LocalTime time = java.time.LocalTime.now();
				if (alarmClock.getQueue().size() > 0) {
					Long alarmTime = alarmClock.getQueue().get(0);
					StringBuilder t = new StringBuilder();
					long total_secs = alarmTime / 1000L;
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
					LocalTime nextAlarm = LocalTime.of(hrs, mins, secs);
					if (time.isAfter(nextAlarm)) {
						alarmClock.buttonRemoveActionPerformed(null);
						JOptionPane.showMessageDialog(null, "ALARM | It is past " + t.toString());
					}
				}
			}
		};

		timer = new Timer(1000, listener);
		timer.start();
	}
}