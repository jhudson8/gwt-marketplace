package hudson.gwtmarketplace.server;

import hudson.gwtmarketplace.domain.manager.AbstractManager;
import hudson.gwtmarketplace.domain.manager.ProductManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearCacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object obj = AbstractManager.getCache().get(ProductManager.TOKEN_VIEWS_BY_IP);
		AbstractManager.getCache().clear();
		if (null != obj)
			AbstractManager.getCache().put(ProductManager.TOKEN_VIEWS_BY_IP, obj);
	}
}
