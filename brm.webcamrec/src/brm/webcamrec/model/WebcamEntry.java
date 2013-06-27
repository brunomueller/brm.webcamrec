/*
 * Created on Feb 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.eclipse.swt.widgets.Display;

import brm.webcamrec.TimedImageReader;
import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.util.gifencoder.AnimatedGifEncoder;

/**
 * @author    schbrm
 */
public class WebcamEntry {
	/**
	 * @uml.property  name="webcamName"
	 */
	private String webcamName;

	/**
	 * @uml.property  name="webcamURL"
	 */
	private String webcamURL;

	/**
	 * @uml.property  name="imageFile"
	 */
	private String imageFile;

	/**
	 * @uml.property  name="readDelay"
	 */
	private String readDelay;

	/**
	 * @uml.property  name="frameTime"
	 */
	private String frameTime;

	/**
	 * @uml.property  name="startTime"
	 */
	private String startTime;

	/**
	 * @uml.property  name="endTime"
	 */
	private String endTime;

	private Timer timer;

	/**
	 * @uml.property  name="imageReader"
	 * @uml.associationEnd  
	 */
	private TimedImageReader imageReader;

	/**
	 * @uml.property  name="gifEncoder"
	 * @uml.associationEnd  
	 */
	private AnimatedGifEncoder gifEncoder;

	/**
	 * This variable is needed to run the asynch update of the UI thread
	 * @uml.property  name="thisWebcam"
	 * @uml.associationEnd  
	 */
	private WebcamEntry thisWebcam;

	/**
	 * @uml.property  name="running"
	 */
	private boolean running = false;

	public static final String TIME_FMT = "dd.MM.yyyy:HH:mm";
	
	public final static String XML_ROOT = "webcamr"; 

	public final static String WEBCAM_ENTRY = "webcamBookmark";

	public final static String WEBCAM_NAME = "webcamName";

	public final static String WEBCAM_URL = "webcamURL";

	public final static String IMAGE_FILE = "imageFile";

	public final static String READ_DELAY = "readDelay";

	public final static String FRAME_TIME = "frameTime";

	public final static String START_TIME = "startTime";

	public final static String END_TIME = "endTime";

	/**
	 * 
	 */
	public WebcamEntry() {
		super();
		// TODO Auto-generated constructor stub
		thisWebcam = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String returnSting = "\n" + "webcamName: "
				+ getWebcamName() + "\n" + "webcamURL: " + getWebcamURL()
				+ "\n" + "imageFile: " + getImageFile() + "\n" + "readDelay: "
				+ getReadDelay() + "\n" + "frameTime: " + getFrameTime() + "\n"
				+ "startTime: " + getStartTime() + "\n" + "endTime: "
				+ getEndTime() + "\n";
		return returnSting;
	}

	public void start() {
		LogPrint.note(this, "start");
		try {
			String imageURL = getWebcamURL();
			String imageOutfile = getImageFile();

			String startTimeS = getStartTime();
			String endTimeS = getEndTime();
			String readDelayS = getReadDelay();
			String frameTimeS = getFrameTime();

			SimpleDateFormat dateFmt = new SimpleDateFormat(
					WebcamEntry.TIME_FMT);
			// switch on strict date parsing
			dateFmt.setLenient(false);

			Date startTime = null;
			Date endTime = null;
			try {
				startTime = dateFmt.parse(startTimeS);
				endTime = dateFmt.parse(endTimeS);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LogPrint.note(this, "start=" + startTime);
			LogPrint.note(this, "end=" + endTime);

			int readDelay = Integer.parseInt(readDelayS);
			int frameTime = Integer.parseInt(frameTimeS);

			// take current time instead of start time to have a more accurate
			// counter 
			long maxImgCount = (endTime.getTime() - startTime.getTime())
					/ (readDelay * 1000);
			LogPrint.note(this, "maxImgCount=" + maxImgCount);

			// create for each run a new timer object
			timer = new Timer();

			// create for each run a new TimedImageReader
			imageReader = new TimedImageReader(this);

			// get gifEncoder
			gifEncoder = new AnimatedGifEncoder();
			
			imageReader.setGifEncoder(gifEncoder);

			// tell imageReader what to read and how many
			imageReader.setImageURL(imageURL);
			//imageReader.setMaxCount(new Long(maxImgCount).intValue());
			imageReader.setMaxCount(new Double(Math.ceil(maxImgCount)).intValue());
			imageReader.setEndTime(endTime);

			// start gifEncoder, define delay between frames
			gifEncoder.start(imageOutfile);
			gifEncoder.setDelay(frameTime);

			// start timer
			// timer.schedule(imageReader, startTime, (readDelay * 1000));
			timer.scheduleAtFixedRate(imageReader, startTime, (readDelay * 1000));
			this.running = true;
			updateDisplay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		LogPrint.note(this, "stop");
		imageReader.cancel();
		gifEncoder.finish();
		this.running = false;
		updateDisplay();
	}

	/**
	 * @return    Returns the webcamName.
	 * @uml.property  name="webcamName"
	 */
	public String getWebcamName() {
		return webcamName;
	}

	/**
	 * @param webcamName    The webcamName to set.
	 * @uml.property  name="webcamName"
	 */
	public void setWebcamName(String bookmarkName) {
		this.webcamName = bookmarkName;
	}

	/**
	 * @return    Returns the readDelay.
	 * @uml.property  name="readDelay"
	 */
	public String getReadDelay() {
		return readDelay;
	}

	/**
	 * @param readDelay    The readDelay to set.
	 * @uml.property  name="readDelay"
	 */
	public void setReadDelay(String delayTime) {
		this.readDelay = delayTime;
	}

	/**
	 * @return    Returns the endTime.
	 * @uml.property  name="endTime"
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime    The endTime to set.
	 * @uml.property  name="endTime"
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return    Returns the imageFile.
	 * @uml.property  name="imageFile"
	 */
	public String getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile    The imageFile to set.
	 * @uml.property  name="imageFile"
	 */
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return    Returns the startTime.
	 * @uml.property  name="startTime"
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime    The startTime to set.
	 * @uml.property  name="startTime"
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return    Returns the webcamURL.
	 * @uml.property  name="webcamURL"
	 */
	public String getWebcamURL() {
		return webcamURL;
	}

	/**
	 * @param webcamURL    The webcamURL to set.
	 * @uml.property  name="webcamURL"
	 */
	public void setWebcamURL(String webcamURL) {
		this.webcamURL = webcamURL;
	}

	/**
	 * @return    Returns the frameTime.
	 * @uml.property  name="frameTime"
	 */
	public String getFrameTime() {
        return frameTime;
    }

	/**
	 * @param frameTime    The frameTime to set.
	 * @uml.property  name="frameTime"
	 */
	public void setFrameTime(String frameTime) {
        this.frameTime = frameTime;
    }

	/**
	 * @return    Returns the running.
	 * @uml.property  name="running"
	 */
	public boolean isRunning() {
        return running;
    }
	
	public String getTimerCountMsg() {
		return  imageReader.getCount() + " of " + imageReader.getMaxCount(); 
	}

	public void updateDisplay() {
		LogPrint.note(this, "updateDisplay");
		Display.getDefault().asyncExec(new Runnable() {
	        public void run() {
				WebcamrecPlugin.getDefault().getWebcamView().getViewer().refresh(thisWebcam);
	    }});

	}

}
