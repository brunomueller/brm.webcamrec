/*
 * Created on Feb 16, 2005 by 
 *
 * 
 */
package brm.webcamrec.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.widgets.Listener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import brm.webcamrec.util.LogPrint;

/**
 * @author    schbrm
 */
public class WebcamEntryCollection {
	private Document webcamDocument;

	private ArrayList webcamList;

	private Listener listener;

	public void writeFile(String fileName) {
		LogPrint.note(this, "writeFile:" + fileName);

		try {

			// Prepare the output file
			File file = new File(fileName);

			Result result = new StreamResult(file);
			// testOutput(new PrintWriter(System.out));
			writeXML(result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Writes the webcam entries to an EXML structure
	 * 
	 * @param out
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private void writeXML(Result result)
			throws TransformerConfigurationException, TransformerException {

		Document doc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			LogPrint.note(this, "Exception" + e);
		}

		// Insert the root element node
		Element rootElement = doc.createElement(WebcamEntry.XML_ROOT);
		doc.appendChild(rootElement);

		// loop through all entires
		Iterator it = webcamList.iterator();
		WebcamEntry returnWebcam = null;
		while (it.hasNext()) {
			WebcamEntry webcamEntry = (WebcamEntry) it.next();

			// Add a Webcam Entry
			Element webcamElement = doc.createElement(WebcamEntry.WEBCAM_ENTRY);
			rootElement.appendChild(webcamElement);

			// add elements that contain the actual data
			Element webcamName = doc.createElement(WebcamEntry.WEBCAM_NAME);
			Element webcamURL = doc.createElement(WebcamEntry.WEBCAM_URL);
			Element webcamImageFile = doc.createElement(WebcamEntry.IMAGE_FILE);
			Element webcamReadDelay = doc.createElement(WebcamEntry.READ_DELAY);
			Element webcamFrameTime = doc.createElement(WebcamEntry.FRAME_TIME);
			Element webcamStartTime = doc.createElement(WebcamEntry.START_TIME);
			Element webcamEndTime = doc.createElement(WebcamEntry.END_TIME);
			webcamElement.appendChild(webcamName);
			webcamElement.appendChild(webcamURL);
			webcamElement.appendChild(webcamImageFile);
			webcamElement.appendChild(webcamReadDelay);
			webcamElement.appendChild(webcamFrameTime);
			webcamElement.appendChild(webcamStartTime);
			webcamElement.appendChild(webcamEndTime);

			// Add a text node to the individual elements
			webcamName.appendChild(doc.createTextNode(webcamEntry
					.getWebcamName()));
			webcamURL.appendChild(doc
					.createTextNode(webcamEntry.getWebcamURL()));
			webcamImageFile.appendChild(doc.createTextNode(webcamEntry
					.getImageFile()));
			webcamReadDelay.appendChild(doc.createTextNode(webcamEntry
					.getReadDelay()));
			webcamFrameTime.appendChild(doc.createTextNode(webcamEntry
					.getFrameTime()));
			webcamStartTime.appendChild(doc.createTextNode(webcamEntry
					.getStartTime()));
			webcamEndTime.appendChild(doc.createTextNode(webcamEntry
					.getEndTime()));
		}
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		// control indentation of file
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		transformer.transform(new DOMSource(doc), result);
	}

	public void readFile(String fileName) {
		LogPrint.note(this, "readFile: " + fileName);
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			webcamDocument = docBuilder.parse(fileName);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			LogPrint.note(this, "ParserConfigurationException");
			e.printStackTrace();

		} catch (SAXException e1) {
			LogPrint.note(this, "SAXException");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			LogPrint.note(this, "IOException");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// System.out.println("NOTE: xmlDoc:\n"
		// + webcamDocument.getDocumentElement());
		webcamDocument.getDocumentElement().normalize();

		webcamList = getWebcamEntries();

	}

	private ArrayList getWebcamEntries() {
		ArrayList webcamList = new ArrayList();
		NodeList webcamBookmarkList;
		Node bookmark;
		Node bmItem;
		webcamBookmarkList = webcamDocument
				.getElementsByTagName(WebcamEntry.WEBCAM_ENTRY);
		// printNode(webcamDocument.getFirstChild());
		for (int i = 0; i < webcamBookmarkList.getLength(); i++) {
			bookmark = webcamBookmarkList.item(i);
			// printNode(bookmark);
			// System.out.println("NOTE: getBookmarks " + i);
			NodeList bookmarkItems = bookmark.getChildNodes();
			WebcamEntry webcamEntry = new WebcamEntry();
			for (int y = 0; y < bookmarkItems.getLength(); y++) {
				bmItem = bookmarkItems.item(y);
				// println(bmItem);

				// System.out.println("NOTE: nodeType " + y + " "
				// + bmItem.getNodeType());

				if (bmItem.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println("NOTE: node.toString:" + bmItem);
					String nodeName = bmItem.getNodeName();
					Node nValue = bmItem.getFirstChild();
					String nodeValue = nValue.getNodeValue();

					if (nodeName.equals(WebcamEntry.WEBCAM_NAME)) {
						webcamEntry.setWebcamName(nodeValue);
					}
					if (nodeName.equals(WebcamEntry.WEBCAM_URL)) {
						webcamEntry.setWebcamURL(nodeValue);
					}
					if (nodeName.equals(WebcamEntry.IMAGE_FILE)) {
						webcamEntry.setImageFile(nodeValue);
					}

					if (nodeName.equals(WebcamEntry.START_TIME)) {
						webcamEntry.setStartTime(nodeValue);
					}

					if (nodeName.equals(WebcamEntry.END_TIME)) {
						webcamEntry.setEndTime(nodeValue);
					}
					if (nodeName.equals(WebcamEntry.READ_DELAY)) {
						webcamEntry.setReadDelay(nodeValue);
					}
					if (nodeName.equals(WebcamEntry.FRAME_TIME)) {
						webcamEntry.setFrameTime(nodeValue);
					}

				}

			}
			webcamList.add(webcamEntry);
			LogPrint.note(this, "Added Webcam :\n" + webcamEntry);
		}
		return webcamList;

	}

	public List elements() {

		LogPrint.note(this, "elements");
		/*
		 * List webcamNames = new ArrayList(); Iterator it =
		 * webcamList.iterator(); while (it.hasNext( )) { WebcamEntry webcamObj =
		 * (WebcamEntry) it.next( ); webcamNames.add(webcamObj.getWebcamName()); }
		 * 
		 * return webcamNames;
		 */
		return webcamList;
	}

	/**
	 * @param listener    The listener to set.
	 * @uml.property  name="listener"
	 */
	public void setListener(Listener l) {
        LogPrint.note(this, "setListener");
        listener = l;
    }

	public WebcamEntry getWebcam(int index) {
		LogPrint.note(this, "getWebcam");
		return (WebcamEntry) webcamList.get(index);

	}

	/**
	 * Get WebcamEntry by Name
	 * 
	 * @param webcamName
	 * @return
	 */
	public WebcamEntry getWebcam(String webcamName) {
		List webcamNames = new ArrayList();
		Iterator it = webcamList.iterator();
		WebcamEntry returnWebcam = null;
		while (it.hasNext()) {

			WebcamEntry bm = (WebcamEntry) it.next();
			if (bm.getWebcamName().equals(webcamName)) {
				returnWebcam = bm;
			}

		}
		return returnWebcam;

	}

	public void add(WebcamEntry webcamEntry) {
		LogPrint.note(this, "add::" + webcamEntry);
		// TODO Auto-generated method stub
		webcamList.add(webcamEntry);

	}

	public void remove(WebcamEntry webcamEntry) {
		LogPrint.note(this, "remove:" + webcamEntry);
		// TODO Auto-generated method stub
		webcamList.remove(webcamEntry);

	}

}
