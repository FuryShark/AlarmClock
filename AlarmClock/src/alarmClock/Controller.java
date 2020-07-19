package alarmClock;

import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Timer;

public class Controller {

	private ActionListener listener;
	private Timer timer;

	private Model model;
	private AlarmClock alarmClock;

	public Controller(Model m, AlarmClock c) {
		model = m;
		alarmClock = c;

		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				model.update();

				LocalTime time = java.time.LocalTime.now();
				if (alarmClock.getQueue().size() > 0) {
					Long time2 = alarmClock.getQueue().get(0);
					long total_secs = time2 / 1000L;
					long total_mins = total_secs / 60L;
					long total_hrs = total_mins / 60L;
					int secs = (int) total_secs % 60;
					int mins = (int) total_mins % 60;
					int hrs = (int) total_hrs % 24;
					LocalTime nextAlarm = LocalTime.of(hrs, mins, secs);
					if (time.isAfter(nextAlarm)) {
						System.out.println("YEH");
					} else {
						System.out.println("NAW");
					}
				}
			}
		};

		timer = new Timer(1000, listener);
		timer.start();
	}
}