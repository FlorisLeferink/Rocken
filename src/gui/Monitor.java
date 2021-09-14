package gui;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import controller.MonitorController;

public class Monitor implements Runnable {
	private MonitorController monitorController;
	private Boolean ischanged;

	public Monitor() {
		monitorController = new MonitorController();
		// Thread thread = new Thread(this);
		// thread.start();
		// GetTimestamp
	}

	public synchronized void performChange() {
		notifyAll();
	}

	public synchronized void awaitChange() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void changeMade() {
		monitorController.changePerformed();
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(3);

				if (monitorController.checkIfEdited()) {
					performChange();
					monitorController.informationUpdated();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
