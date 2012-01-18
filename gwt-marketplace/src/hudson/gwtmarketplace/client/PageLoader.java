package hudson.gwtmarketplace.client;

import hudson.gwtmarketplace.client.pages.MainPage;
import hudson.gwtmarketplace.client.pages.product.EditProductPage;
import hudson.gwtmarketplace.client.pages.product.NewProductPage;
import hudson.gwtmarketplace.client.pages.product.ProductSearchPage;
import hudson.gwtmarketplace.client.pages.product.ViewProductPage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.gwtpages.client.page.LoadedPageContainer;
import com.google.gwt.gwtpages.client.page.loader.PageLoadCallback;
import com.google.gwt.gwtpages.client.page.loader.StandardPageLoader;

public class PageLoader extends StandardPageLoader {

	public static final String PAGE_DEFAULT = "";
	public static final String PAGE_VIEW_PRODUCT = "_view";
	public static final String PAGE_SEARCH = "_search";
	public static final String PAGE_NEW_PRODUCT = "_new";
	public static final String PAGE_EDIT_PRODUCT = "_edit";

	public static final String SEARCH_CATEGORY = "cat";

	private ViewProductPage viewProductPage;

	public PageLoader() {

	}

	@Override
	public boolean isValidPageToken(String token) {
		return super.isValidPageToken(token) || !token.startsWith("_");
	}

	/**
	 * Allow a wildcard for the product alias as the page token because all
	 * registered pages start with '_'
	 */
	@Override
	public void getPage(String pageToken, PageLoadCallback pageHandler) {
		if (null == pageToken || pageToken.equals(PAGE_DEFAULT)
				|| pageToken.startsWith("_")) {
			super.getPage(pageToken, pageHandler);
		} else {
			if (null == viewProductPage) viewProductPage = GWT.create(ViewProductPage.class);
			pageHandler.onPageFound(new LoadedPageContainer(pageToken, viewProductPage,
					null, this));
		}
	}

	@Override
	public void registerPages() {
		registerPage(PAGE_DEFAULT, MainPage.class);
		registerPage(PAGE_NEW_PRODUCT, NewProductPage.class,
				AuthenticationPageEventHandler.createPageAttributes());
		registerPage(PAGE_VIEW_PRODUCT, ViewProductPage.class);
		registerPage(PAGE_EDIT_PRODUCT, EditProductPage.class,
				AuthenticationPageEventHandler.createPageAttributes());
		registerPage(PAGE_SEARCH, ProductSearchPage.class);
	}
}