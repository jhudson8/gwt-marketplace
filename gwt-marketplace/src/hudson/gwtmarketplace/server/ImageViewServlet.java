/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server;

import hudson.gwtmarketplace.domain.manager.ProductManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final ProductManager productMgr = new ProductManager();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String key = req.getParameter("key");
		if (null == key) {
			resp.setStatus(404);
		}
		else {
			byte[] data = productMgr.getImageData(Long.parseLong(key));
			if (null == data) resp.setStatus(404);
			else resp.getOutputStream().write(data);
		}
	}
}
