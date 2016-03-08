package me.yangmang.frame;


public class UpdateHelper {
	private Thread thread;
	private boolean isEnable = true;
	private MainFrame frame;

	public UpdateHelper(MainFrame inFrame) {
		this.frame = inFrame;
		thread = new Thread() {
			public void run() {
				while (isEnable) {

//					System.out.println("Log");

					frame.display.asyncExec(new Runnable() {
						@Override
						public void run() {
							frame.updateSensorData();

						}
					});

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};
		thread.start();
	}

	public void end() {
		this.isEnable = false;
	}
}
