package alarmClock;

public class Clock {

	public static void main(String[] args) {
		Model model = new Model();
		AlarmClock alarmClock = new AlarmClock(model);
		alarmClock.setVisible(true);

	}

}
