package edu.gwu.csci6231.device.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.frame.CameraPanel;
import edu.gwu.csci6231.frame.FrameUtil;

public class DataModelCamera extends DataModel {

	public static final int ONE_MOVEMENT = 15;

	protected int x, y, w, h;
	protected double zoom = 1;
	protected double zoomMax = 4;
	protected double zoomMin = 0.5;
	protected int oriW, oriH;

	protected boolean isRemoved = false;
	
	protected ImageLoader loader;
	protected ImageData[] imageDatas;
	protected Image[] images;
	protected int imageIndex = 0;
	protected DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DataModelCamera(String modelName, DeviceProvider provider) {
		this.modelName = modelName;
		this.provider = provider;
	}

	public void loadSrc(String fileName) {
		loader = new ImageLoader();
		imageDatas = loader.load(fileName);
		if (imageDatas != null && imageDatas.length > 0) {
			oriW = imageDatas[0].width;
			oriH = imageDatas[0].height;

			zoomMin = Math.max(
					(double) ((double) CameraPanel.VIDEO_WIDTH / oriW),
					(double) ((double) CameraPanel.VIDEO_HEIGHT / oriH));
			
			if(zoom<zoomMin)
				zoom = zoomMin;
			
			zoom = zoomMin * 1.25;
			
			x =(int)( (oriW-CameraPanel.VIDEO_WIDTH/zoom)/2);
			y =(int)( (oriH-CameraPanel.VIDEO_HEIGHT/zoom)/2);

//			 System.out.println(oriW+" "+oriH);
			this.updateOutput();
			
			convertImageDatasToImages();
			
		}
	}

	private void updateOutput() {
		w = (int) (CameraPanel.VIDEO_WIDTH / zoom);
		h = (int) (CameraPanel.VIDEO_HEIGHT / zoom);
		if (x + w > oriW)
			x = oriW - w;
		if (y + h > oriH)
			y = oriH - h;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
//
//		System.out.printf("%d %d %d %d %d %d %f\n", x, y, w, h, oriW, oriH,zoomMin);
	}

	public void zoom(boolean in) {
		if (in) {
			zoom = 1.25 * zoom;
		} else {
			zoom = zoom / 1.25;
		}
		if (zoom > zoomMax)
			zoom = zoomMax;
		if (zoom < zoomMin)
			zoom = zoomMin;
	}

	public void moveLeftOrRight(boolean toLeft) {
		x = (int) (x + (toLeft ? -1 : 1) * ONE_MOVEMENT / zoom);
		this.updateOutput();
	}

	public void moveUpOrDown(boolean toUp) {
		y = (int) (y + (toUp ? -1 : 1) * ONE_MOVEMENT / zoom);
		this.updateOutput();
	}

	@Override
	public void updateValue() {
		if (imageDatas != null) {
			this.updateOutput();
			imageIndex = (imageIndex + 1) % imageDatas.length;
			
//			System.out.println(imageIndex);
			this.setChanged();
			this.notifyObservers();
		}

	}

	public ImageData getImageData() {
		if (this.imageDatas != null)
			return this.imageDatas[this.imageIndex];
		return null;
	}
	
	public Image getImage(){
		if (this.images !=null)
			return this.images[this.imageIndex];
		return null;
	}

	public String getTime() {
		return format.format(new Date());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
	
	public void removeModel () {
		this.isRemoved = true;
		this.setChanged();
		this.notifyObservers(FrameUtil.MSG_REMOVE_MODEL);
	}
	
	public boolean isRemoved(){
		return this.isRemoved;
	}

	public String toString(){
		return modelName+" images:"+imageDatas.length+" x,y:"+x+","+y+" w,h:"+w+","+h;
	}
	
	/**
	 * read GIF and convert ImageData to Image
	 */
	protected void convertImageDatasToImages() {
		images = new Image[imageDatas.length];

		// Step 1: Determine the size of the resulting images.
		int width = imageDatas[0].width;
		int height = imageDatas[0].height;
		
		System.out.println(width+" "+height);

		// Step 2: Construct each image.
		int transition = SWT.DM_FILL_BACKGROUND;
		for (int i = 0; i < imageDatas.length; i++) {
			ImageData id = imageDatas[i];
			images[i] = new Image(null, width, height);
			GC gc = new GC(images[i]);

			// Do the transition from the previous image.
			switch (transition) {
			case SWT.DM_FILL_NONE:
			case SWT.DM_UNSPECIFIED:
				// Start from last image.
				gc.drawImage(images[i - 1], 0, 0);
				break;
			case SWT.DM_FILL_PREVIOUS:
				// Start from second last image.
				gc.drawImage(images[i - 2], 0, 0);
				break;
			default:
				// DM_FILL_BACKGROUND or anything else,
				// just fill with default background.
				gc.setBackground(FrameUtil.COLOR_CAMERA_BG);
				gc.fillRectangle(0, 0, width, height);
				break;
			}

			// Draw the current image and clean up.
			Image img = new Image(null, id);
			gc.drawImage(img, 0, 0, id.width, id.height, id.x, id.y, id.width,
					id.height);
			img.dispose();
			gc.dispose();

			// Compute the next transition.
			// Special case: Can't do DM_FILL_PREVIOUS on the
			// second image since there is no "second last"
			// image to use.
			transition = id.disposalMethod;
			if (i == 0 && transition == SWT.DM_FILL_PREVIOUS)
				transition = SWT.DM_FILL_NONE;
		}
	}
}
