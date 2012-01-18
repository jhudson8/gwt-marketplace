/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server;

import hudson.gwtmarketplace.client.exception.InvalidAccessException;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.domain.manager.ProductManager;
import hudson.gwtmarketplace.server.util.ImageUtil;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class BlobHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static transient ProductManager  productMgr = new ProductManager();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
		String rtnKey = null;

		Map<String, BlobKey> blobs = service.getUploadedBlobs(req);
		String id = req.getParameter("key");
		User user = UserServiceFactory.getUserService().getCurrentUser();
		Product product = productMgr.getById(Long.parseLong(id));
		if (null != user) {
			if (null != product && product.getUserId().equals(user.getUserId())) {
				if (blobs.size() == 1) {
					BlobKey origKey = blobs.values().iterator().next();
					byte[] arr = ImageUtil.iconize(origKey.getKeyString());
					if (null != arr) {
						product.setIconKey(origKey.getKeyString());
						try {
							productMgr.update(product);
							rtnKey = product.getIconKey();
						}
						catch (InvalidAccessException e) {
							service.delete(origKey);
						}
					}
				}
				else {
					// bad transmission
					for (BlobKey blobKey : blobs.values()) {
						service.delete(blobKey);
					}
				}
			}
			else {
				// bad transmission
				for (BlobKey blobKey : blobs.values()) {
					service.delete(blobKey);
				}
			}
		}
		if (null != rtnKey)
			resp.getOutputStream().write(rtnKey.getBytes());
	}
}