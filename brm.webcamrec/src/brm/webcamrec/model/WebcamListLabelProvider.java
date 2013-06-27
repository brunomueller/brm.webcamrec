/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import brm.webcamrec.ISharedImages;
import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;

/**
 * @author schbrm
 */
public class WebcamListLabelProvider extends LabelProvider implements
		ITableLabelProvider, ISharedImages {

	public String getColumnText(Object obj, int index) {
		LogPrint.note(this, "getColumnText" + index + " "
				+ obj.getClass().getName());
		String returnString = "";
		WebcamEntry webcam = ((WebcamEntry) obj);

		switch (index) {
		case 0:
			// return webcam name
			returnString = webcam.getWebcamName();
			break;
		case 1:
			// return for column 1
			if (webcam.isRunning()) {
				returnString = webcam.getTimerCountMsg() + " " + webcam.getReadDelay();	
			}
			else {
				returnString = "Stopped at " + webcam.getEndTime();
			}
			
		default:
			break;
		}

		return returnString;
	}

	public Image getColumnImage(Object obj, int index) {
		LogPrint.note(this, "getColumnImage index=" + index);

		// check if we are at column 1 (0) then return proper image
		// else return null

		if (index == 0) {
			WebcamEntry webcamEntry = (WebcamEntry) obj;
			String image = ISharedImages.LAUNCH_RUN;

			if (webcamEntry.isRunning() == false) {
				image = ISharedImages.NAV_STOP;
			} else {
				image = ISharedImages.LAUNCH_RUN;
			}
			ImageDescriptor imgDesc = WebcamrecPlugin
					.imageDescriptorFromPlugin(WebcamrecPlugin.ID, image);
			return imgDesc.createImage();
		}
		return null;
	}

	public Image getImage(Object element) {
		LogPrint.note(this, "getImage");
		// TODO Auto-generated method stub

		return super.getImage(element);
	}
}