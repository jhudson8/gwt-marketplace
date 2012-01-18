/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.commands.GetProductCategoriesCommand;
import hudson.gwtmarketplace.client.components.LabeledListBox;
import hudson.gwtmarketplace.client.components.LabeledTextBox;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.search.SearchResults;
import hudson.gwtmarketplace.client.service.ProductService;
import hudson.gwtmarketplace.client.service.ProductServiceAsync;
import hudson.gwtmarketplace.client.util.WidgetUtil;

import java.util.ArrayList;
import java.util.HashMap;

import org.look.widgets.client.datatable.ColumnMetaData;
import org.look.widgets.client.datatable.DataRecord;
import org.look.widgets.client.datatable.DataTable;
import org.look.widgets.client.datatable.DataTable.ChangeSortListener;
import org.look.widgets.client.datatable.MetaData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.page.AsyncPageCallback;
import com.google.gwt.gwtpages.client.page.impl.UiBoundPage;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SubmitButton;

public class ProductSearchPage extends UiBoundPage<FlowPanel> implements
		ChangeSortListener, ClickHandler {

	private static ProductServiceAsync productService = GWT
			.create(ProductService.class);
	private static DateTimeFormat dateFormat = DateTimeFormat
			.getMediumDateFormat();
	private static NumberFormat ratingFormat = NumberFormat.getFormat("0.00");
	private HashMap<String, String> params = new HashMap<String, String>();
	private ArrayList<String> generalParams = new ArrayList<String>();

	@UiField
	LabeledTextBox searchFor;
	@UiField
	LabeledTextBox tag;
	@UiField
	LabeledListBox category;
	@UiField
	SimplePanel searchResultsContainer;
	@UiField
	SimplePanel infoContainer;
	@UiField
	DisclosurePanel searchPanel;
	@UiField
	SubmitButton searchBtn;

	private DataTable table;
	private MetaData metaData;
	private String sortColumn = "name";
	private boolean orderingAsc = true;
	private Integer knownRowCount;

	@Override
	protected void onConstruct(FlowPanel view) {
		metaData = new MetaData();
		metaData.setColumnData(new ColumnMetaData[] {
				new SimpleColumn("Product", "name"),
				new SimpleColumn("Website", "website"),
				new SimpleColumn("Last Updated", "updatedDate"),
				new SimpleColumn("Rating", "rating"), });
		table = new DataTable();
		table.setSortListener(this);
		searchResultsContainer.add(table);
		searchPanel.setHeader(new Label("View advanced search options"));
		searchBtn.addClickHandler(this);

		new GetProductCategoriesCommand() {
			@Override
			public void onSuccess(ArrayList<Category> result) {
				WidgetUtil.load(category.getComponent(), result,
						"Select a category");
			}
		}.execute();
	}

	private void onSearch() {
		params.clear();
		generalParams.clear();
		if (null != tag.getComponent().getValue()
				&& tag.getComponent().getValue().trim().length() > 0) {
			params.put("tag", tag.getComponent().getValue().trim());
		}
		int index = category.getComponent().getSelectedIndex();
		if (index > 1) {
			params.put("category", category.getComponent().getValue(index));
		}
		String searchFor = this.searchFor.getComponent().getValue().trim();
		if (null != searchFor && searchFor.length() > 0) {
			String[] arr = searchFor.split(" ");
			if (arr.length > 0) {
				for (String s : arr)
					generalParams.add(s);
			}
		}
		resetGrid();
	}

	@Override
	public void onEnterPage(PageParameters parameters,
			PageRequestSession session, AsyncPageCallback callback) {
		generalParams.clear();
		if (parameters.listSize() > 0) {
			String[] arr = parameters.asString(0).split(" ");
			params.clear();
			for (String s : arr) {
				if (s.length() > 0) {
					int index = s.indexOf(':');
					if (index > 0 && s.length() > index) {
						String key = s.substring(0, index);
						String value = s.substring(index + 1);
						params.put(key, value);
					} else {
						generalParams.add(s);
					}
				}
			}
		}
		String _tag = params.get("tag");
		if (null != _tag)
			tag.getComponent().setValue(_tag);
		String _category = params.get("category");
		if (null != _category)
			WidgetUtil.selectValue(category.getComponent(), _category);
		if (generalParams.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : generalParams) {
				if (sb.length() > 0)
					sb.append(" ");
				sb.append(s);
			}
			searchFor.getComponent().setValue(sb.toString());
		}
		resetGrid();
	}

	@Override
	public void onExitPage() {
		generalParams.clear();
		params.clear();
		sortColumn = "name";
		orderingAsc = true;
		knownRowCount = null;
		tag.getComponent().setValue(null);
		searchFor.getComponent().setValue(null);
		category.setSelectedIndex(0);
	}

	private void resetGrid() {
		showInfo("Searching, please wait...");
		productService.search(params, generalParams, 0, 100, sortColumn,
				orderingAsc, knownRowCount,
				new AsyncCallback<SearchResults<Product>>() {
					@Override
					public void onSuccess(SearchResults<Product> result) {
						knownRowCount = result.getTotalRowCount();
						ArrayList<Product> entries = result.getEntries();
						if (entries.size() == 0) {
							showInfo("No products could be found matching the requested criteria.");
						} else {
							hideInfo();
							DataRecord[] results = new DataRecord[entries
									.size()];
							for (int i = 0; i < entries.size(); i++) {
								results[i] = new ProductDataRecord(result
										.getEntries().get(i));
							}
							table.AssignResults(results, metaData);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						showInfo("Sorry, an error occured.  "
								+ caught.getMessage());
						caught.printStackTrace();
					}
				});
	}

	private void showInfo(String message) {
		searchResultsContainer.setVisible(false);
		infoContainer.clear();
		infoContainer.setVisible(true);
		infoContainer.add(new Label(message));
	}

	private void hideInfo() {
		infoContainer.clear();
		infoContainer.setVisible(false);
		searchResultsContainer.setVisible(true);
	}

	@Override
	public void onChangeSort(int newColumn, boolean newDesc) {
		// TODO Auto-generated method stub

	}

	private class SimpleColumn implements ColumnMetaData {
		private String title;
		private String width;
		private String key;

		public SimpleColumn(String title, String key) {
			this.title = title;
			this.width = "";
			this.key = key;
		}

		public String getTitle() {
			return title;
		}

		public String getWidth() {
			return "";
		}

		public String getHtml(DataRecord dataRecord) {
			return ((ProductDataRecord) dataRecord).getValue(key);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(searchBtn)) {
			onSearch();
		}
	}

	private class ProductDataRecord implements DataRecord {
		private Product product;

		public ProductDataRecord(Product product) {
			this.product = product;
		}

		public String getValue(String key) {
			if (key.equals("name")) {
				return "<a href=\"#" + product.getAlias() + "\" >"
						+ product.getName() + "</a>";
			} else if (key.equals("updatedDate")) {
				return dateFormat.format(product.getUpdatedDate());
			} else if (key.equals("rating")) {
				if (null == product.getRating())
					return "unrated";
				else
					return ratingFormat.format(product.getRating());
			} else if (key.equals("website")) {
				return "<a href=\"" + product.getWebsiteUrl()
						+ "\" target=\"_blank\">" + product.getWebsiteUrl()
						+ "</a>";
			} else
				return null;
		}
	}
}