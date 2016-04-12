package edu.gwu.csci6231.device.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	protected Image[] imags;
	protected ImageLoader loader;
	protected ImageData[] images;
	protected int imageIndex = 0;
	protected DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DataModelCamera(String modelName, DeviceProvider provider) {
		this.modelName = modelName;
		this.provider = provider;
	}

	public void loadSrc(String fileName) {
		loader = new ImageLoader();
		images = loader.load(fileName);
		if (images != null && images.length > 0) {
			int commonIndex = getIndexOfTemplateImage(images);
			oriW = images[commonIndex].width;
			oriH = images[commonIndex].height;

			zoomMin = Math.max(
					(double) ((double) CameraPanel.VIDEO_WIDTH / oriW),
					(double) ((double) CameraPanel.VIDEO_HEIGHT / oriH));
			
			if(zoom<zoomMin)
				zoom = zoomMin;

//			 System.out.println(oriW+" "+oriH);
			this.updateOutput();
			
			List<ImageData>reformed = new ArrayList<ImageData>();
			for(ImageData item:images){
				if(item.width==oriW && item.height==oriH)
					reformed.add(item);
			}
			
			images = reformed.toArray(new ImageData[0]);
			
//			System.out.println(images.length);
			
		}
	}

	private int getIndexOfTemplateImage(ImageData[] imageDatas) {
		Map<Integer,Integer> data = new HashMap<Integer,Integer>();
		for(int i=0;i<imageDatas.length;i++){
			int tmpKey = imageDatas[i].width*10000+imageDatas[i].height;
			if(data.get(tmpKey)==null)
				data.put(tmpKey, new Integer(0));
			data.put(tmpKey, data.get(tmpKey)+1);
			
//			System.out.printf("%s size: %d,%d\n",modelName,imageDatas[i].width,imageDatas[i].height);
		}
		int mostKey = -1;
		int sum = 0;
		for(Integer key:data.keySet()){
			if(data.get(key)>sum){
				mostKey = key;
				sum = data.get(key);
			}
		}
		for(int i=0;i<imageDatas.length;i++){
			if(imageDatas[i].width*10000+imageDatas[i].height==mostKey){
				return i;
			}
		}
		return 0;
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
		if (images != null) {
			this.updateOutput();
			imageIndex = (imageIndex + 1) % images.length;
			
//			System.out.println(imageIndex);
			this.setChanged();
			this.notifyObservers();
		}

	}

	public ImageData getImageData() {
		if (this.images != null)
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
		return modelName+" images:"+images.length+" x,y:"+x+","+y+" w,h:"+w+","+h;
	}
}
