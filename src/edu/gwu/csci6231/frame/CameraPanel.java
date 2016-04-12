package edu.gwu.csci6231.frame;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.device.model.DataModelCamera;

public class CameraPanel  extends Composite implements Observer {

	public static int HEIGHT = 210;
	public static int WIDTH = 330;
	public static int VIDEO_HEIGHT = 200;
	public static int VIDEO_WIDTH = 200;
	public static int PADDING = 5;
	public static int BUTTON_SIZE = 40;
	
	protected Canvas videoCanvas;
	
	protected DataModelCamera model;
	
	
	protected ImageButton btnUp,btnDown,btnLeft,btnRight,btnZoomIn,btnZoomOut,btnRemove;
	
	public CameraPanel(Composite parent, int mode, DataModel model){
		this(parent,mode);
		this.model = (DataModelCamera)model;
	}
	

	
	

	
	private CameraPanel(Composite parent, int mode) {
		super(parent, mode);
		
		
//		this.setBackgroundMode(SWT.INHERIT_FORCE);
		
		this.setBackground(FrameUtil.COLOR_CAMERA_BG);
		
		videoCanvas = new Canvas(this, SWT.NONE);
		videoCanvas.setBounds(PADDING, PADDING, VIDEO_WIDTH, VIDEO_HEIGHT);
		/*
		 * draw video content according to current setting (x,y,w,h: zoom)
		 */
		videoCanvas.addPaintListener(new PaintListener(){
			@Override
			public void paintControl(PaintEvent e) {
				
//				e.gc.setBackground(FrameUtil.INDICATOR_FGS[0]);
				
//				e.gc.fillRectangle(0, 0, 200, 200);
				Image image = model.getImage();
				try {
					e.gc.drawImage(image,model.getX(),model.getY(),model.getW(),model.getH(),0,0,VIDEO_WIDTH,VIDEO_HEIGHT);
				} catch (Exception e1) {
					System.out.println(model);
					e1.printStackTrace();
				}
				
				
				e.gc.setForeground(FrameUtil.COLOR_WHITE);
				e.gc.setFont(FrameUtil.FONT_TIME);
				e.gc.drawString(model.getModelName(), 1, 1, true);
				e.gc.drawString(model.getTime(), 101, 1, true);
				e.gc.drawString(model.getModelName(), -1, -1, true);
				e.gc.drawString(model.getTime(), 101, -1, true);
				e.gc.drawString(model.getModelName(), -1, 1, true);
				e.gc.drawString(model.getTime(), 99, -1, true);
				e.gc.drawString(model.getModelName(), 1, -1, true);
				e.gc.drawString(model.getTime(), 99, 1, true);
				e.gc.setForeground(FrameUtil.COLOR_BLACK);
				e.gc.setFont(FrameUtil.FONT_TIME);
				e.gc.drawString(model.getModelName(), 0, 0, true);
				e.gc.drawString(model.getTime(), 100, 0, true);
				
				
			}
			
		});
		
		/*
		 * initialize buttons
		 */
		btnDown = new ImageButton(this,SWT.NONE);
		btnDown.setImage(new Image(this.getDisplay(), "./down.png"));
		btnDown.setBounds(VIDEO_WIDTH+BUTTON_SIZE+5,(int)(BUTTON_SIZE*1.5+10),BUTTON_SIZE,BUTTON_SIZE);
		
		btnUp = new ImageButton(this,SWT.NONE);
		btnUp.setImage(new Image(this.getDisplay(), "./up.png"));
		btnUp.setBounds(VIDEO_WIDTH+BUTTON_SIZE+5,10,BUTTON_SIZE,BUTTON_SIZE);
		
		btnLeft = new ImageButton(this,SWT.NONE);
		btnLeft.setImage(new Image(this.getDisplay(), "./left.png"));
		btnLeft.setBounds(VIDEO_WIDTH+10,20+(int)(BUTTON_SIZE/2),BUTTON_SIZE,BUTTON_SIZE);
		
		btnRight = new ImageButton(this,SWT.NONE);
		btnRight.setImage(new Image(this.getDisplay(), "./right.png"));
		btnRight.setBounds(VIDEO_WIDTH+BUTTON_SIZE*2,20+(int)(BUTTON_SIZE/2),BUTTON_SIZE,BUTTON_SIZE);
	
		btnZoomIn = new ImageButton(this,SWT.NONE);
		btnZoomIn.setImage(new Image(this.getDisplay(), "./zoomin.png"));
		btnZoomIn.setBounds(VIDEO_WIDTH+20,20+BUTTON_SIZE*5/2,BUTTON_SIZE,BUTTON_SIZE);
	
		btnZoomOut = new ImageButton(this,SWT.NONE);
		btnZoomOut.setImage(new Image(this.getDisplay(), "./zoomout.png"));
		btnZoomOut.setBounds(VIDEO_WIDTH+20+BUTTON_SIZE+10,20+BUTTON_SIZE*5/2,BUTTON_SIZE,BUTTON_SIZE);
	
		btnRemove = new ImageButton(this,SWT.NONE);
		btnRemove.setImage(new Image(this.getDisplay(), "./close.png"));
		btnRemove.setBounds(VIDEO_WIDTH+50,20+BUTTON_SIZE*7/2,BUTTON_SIZE,BUTTON_SIZE);
	
	
		this.addListener();
	}
	
	private void addListener(){
		btnZoomIn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_ZOOM_IN);
			}
		});
		btnZoomOut.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_ZOOM_OUT);
			}
		});
		
		btnLeft.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_LEFT);
			}
		});
		btnRight.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_RIGHT);
			}
		});
		
		btnUp.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_UP);
			}
		});
		btnDown.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_DOWN);
			}
		});
		
		btnRemove.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				model.takeAction(DataModel.CMD_CAMERA_REMOVE);
			}
		});
		
	}

	@Override
	public void update(Observable o, Object msg) {
		
		if(msg!=null&&msg.equals(FrameUtil.MSG_REMOVE_MODEL)){
			return;
		}
		
		if(this.model==null || this.model.isRemoved())
			return;
		
		if (this.isDisposed()||this.getDisplay().isDisposed())
			return;
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if(model==null || model.isRemoved())
					return;
				redraw();
			}
		});
	}
	
	public boolean hasModel(DataModel model){
		return this.model!=null&&this.model.getModelName().equals(model.getModelName());
	}

}
