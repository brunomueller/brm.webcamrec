/*
 * Created on May 2, 2004
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package brm.webcamrec;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.util.gifencoder.AnimatedGifEncoder;

/**
 * @author    schbrm
 */
public class TimedImageReader extends TimerTask {

	private static final String TIMESTAMP_FORMAT = "dd.MM.yyyy:HH:mm:ss";

	/**
	 * @uml.property  name="webcam"
	 * @uml.associationEnd  
	 */
	private WebcamEntry webcam;

	/**
	 * Comment for <code>count</code>
	 * @uml.property  name="count"
	 */
	private int count;

	/**
	 * Comment for <code>imageURL</code>
	 * @uml.property  name="imageURL"
	 */
	private String imageURL;

	/**
	 * Comment for <code>maxCount</code>
	 * @uml.property  name="maxCount"
	 */
	private int maxCount;

	/**
	 * Comment for <code>endTime</code>
	 * @uml.property  name="endTime"
	 */
	private Date endTime;

	/**
	 * @uml.property  name="gifEncoder"
	 * @uml.associationEnd  
	 */
	private AnimatedGifEncoder gifEncoder;

	// used for console view
	private MessageConsole msgConsole;

	private MessageConsoleStream msgStream;

	/**
	 * 
	 */
	public TimedImageReader(WebcamEntry webcam) {
		super();
		this.webcam = webcam;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Add the textString to the lower left hand corner
	 * 
	 * @param image
	 * @param textString
	 */
	private void addText(BufferedImage image, String textString) {

		// textString = "A " + textString + " yy";

		LogPrint.note(this, textString);

		int offset = 2;
		// get the graphics context
		Graphics2D graphics = (Graphics2D) image.getGraphics();

		// get the current font
		Font f = graphics.getFont();

		// Nun den RenderContext des Zeichensatzes
		// ermitteln (irgendwohin wird ja der Zeichen-
		// satz geschrieben. Dieser Kontext bestimmt die
		// endgültige Zeichengröße und Ausrichtung):
		FontRenderContext frc = graphics.getFontRenderContext();

		LineMetrics lm = f.getLineMetrics(textString, frc);

		// get font infromation to calculate the offset for
		// drawing the string information
		float descent = lm.getDescent();
		float leading = lm.getLeading();

		// color and transparency info
		int red = 255;
		int green = 255;
		int blue = 0;
		int transparency = 75; // in percent

		// Wenn man aber das verbrauchte Rechteck des
		// Strings haben will, dann verwendet man:
		Rectangle2D r = f.getStringBounds(textString, frc);

		int textHeight = new Double(r.getHeight()).intValue() - 1;
		int textWidth = new Double(r.getWidth()).intValue() + 1;

		Color fgColor = new Color(red, green, blue, 255 * transparency / 100);
		Color bgColor = new Color(0, 0, 0, 255 * transparency / 100);

		graphics.setColor(bgColor);

		graphics.fillRect(offset, image.getHeight() - textHeight - offset,
				textWidth, textHeight);

		graphics.setColor(fgColor);

		graphics.drawString(textString, offset, image.getHeight() - descent
				- leading - offset);

	}

	/**
	 * @return    Returns the imageURL.
	 * @uml.property  name="imageURL"
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @return    Returns the maxCount.
	 * @uml.property  name="maxCount"
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * Returns the current timestamp
	 * 
	 * @return
	 */
	private String getTimeStamp() {
		String timeInfo = new SimpleDateFormat(
				TimedImageReader.TIMESTAMP_FORMAT).format(new Date());
		return timeInfo;
	}

	/**
	 * Read an image from a URL location
	 * 
	 * @param imageURL
	 * @return
	 * @throws MalformedURLException
	 */
	private BufferedImage readImage(String imageURL)
			throws MalformedURLException, IOException {
		LogPrint.note(this, imageURL);
		BufferedImage image = null;
		URL url = null;
		url = new URL(imageURL);
		image = ImageIO.read(url);
		return (image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		// AnimatedGifEncoder gifEncoder = getGifEncoder();

		count = count + 1;
		LogPrint.note(this, "Current " + count + " of " + maxCount);

		BufferedImage realImg;
		try {
			realImg = readImage(imageURL);
			String timeStamp = getTimeStamp();
			addText(realImg, count + " of " + maxCount + " " + timeStamp);
			writeToConsole(count + " of " + maxCount + " " + timeStamp);
			gifEncoder.addFrame(realImg);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LogPrint.note(this, "readImage:MalformedURLException");
			writeToConsole("readImage:MalformedURLException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			writeToConsole("readImage:IOException");
		}
		// need special way to update SWT stuff in UI thread
		webcam.updateDisplay();

		// if ( new Date().after(endTime) ) {
		// webcam.stop();
		// }

		if (count >= maxCount) {
			webcam.stop();
		}

	}

	/**
	 * @param imageURL    The imageURL to set.
	 * @uml.property  name="imageURL"
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @param maxCount    The maxCount to set.
	 * @uml.property  name="maxCount"
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * @return    endTime
	 * @uml.property  name="endTime"
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param  endTime
	 * @uml.property  name="endTime"
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return    Returns the gifEncoder.
	 * @uml.property  name="gifEncoder"
	 */
	public AnimatedGifEncoder getGifEncoder() {
        return gifEncoder;
    }

	/**
	 * @param gifEncoder    The gifEncoder to set.
	 * @uml.property  name="gifEncoder"
	 */
	public void setGifEncoder(AnimatedGifEncoder gifEncoder) {
        this.gifEncoder = gifEncoder;
    }

	/**
	 * @return    Returns the count.
	 * @uml.property  name="count"
	 */
	public int getCount() {
        return count;
    }

	private void writeToConsole(String consoleText) {
		if (msgConsole == null) {
			msgConsole = WebcamrecPlugin.getDefault().getMessageConsole();
			msgStream = msgConsole.newMessageStream();
		}
		msgStream.println(webcam.getWebcamName() + "::" + consoleText);
	}

}