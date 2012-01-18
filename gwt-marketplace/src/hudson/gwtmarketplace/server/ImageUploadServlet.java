/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server;

import hudson.gwtmarketplace.client.exception.InvalidAccessException;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.domain.manager.AbstractManager;
import hudson.gwtmarketplace.domain.manager.ProductManager;
import hudson.gwtmarketplace.server.model.ProductImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.googlecode.objectify.Key;

public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static transient ProductManager  productMgr = new ProductManager();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		ServletFileUpload upload = new ServletFileUpload();

		Map<String, String> parameters = new HashMap<String, String>();
		Image resizedImage = null;
		
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();
			    String name = item.getFieldName();
			    InputStream stream = item.openStream();
			    if (item.isFormField()) {
			    	parameters.put(name, toString(stream));
			    } else {
			    	resizedImage = resize(stream);
			    }
			}
		}
		catch (Exception e) {
			response.sendError(500);
		}
		String productId = parameters.get("key");
		if (null != productId && null != resizedImage) {
			try {
				String iconKey = productMgr.setImageData(Long.parseLong(productId), resizedImage.getImageData());
				if (null != iconKey) {
					response.getOutputStream().write(iconKey.getBytes());
				}
			}
			catch (InvalidAccessException e) {
				e.printStackTrace();
			}
		}

	}

	private Image resize(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = -1;
		while ((bytesRead = is.read(buffer))  > 0) {
			out.write(buffer, 0, bytesRead);
		}
		is.close();
		ImagesService imageService = ImagesServiceFactory.getImagesService();
        Image oldImage = ImagesServiceFactory.makeImage(out.toByteArray());
        Transform resize = ImagesServiceFactory.makeResize(160, 600);
        Image newImage = imageService.applyTransform(resize, oldImage);
        return newImage;
	}

	private String toString(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = -1;
		while ((bytesRead = is.read(buffer))  > 0) {
			out.write(buffer, 0, bytesRead);
		}
		return new String(out.toByteArray());
	}
}