/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.event;

import hudson.gwtmarketplace.client.model.Category;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CategoriesUpdatedEvent extends
		GwtEvent<CategoriesUpdatedEvent.CategoriesUpdateHandler> {

	public interface CategoriesUpdateHandler extends EventHandler {
		void onCategoriesUpdated(ArrayList<Category> categories);
	}

	private ArrayList<Category> categories;

	public CategoriesUpdatedEvent(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public static final GwtEvent.Type<CategoriesUpdatedEvent.CategoriesUpdateHandler> TYPE = new GwtEvent.Type<CategoriesUpdateHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CategoriesUpdateHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CategoriesUpdatedEvent.CategoriesUpdateHandler handler) {
		handler.onCategoriesUpdated(categories);
	}

}
