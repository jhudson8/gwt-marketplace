/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.event;

import hudson.gwtmarketplace.client.model.Top10Lists;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TopsUpdatedEvent extends
		GwtEvent<TopsUpdatedEvent.TopsUpdatedHandler> {
	
	public interface TopsUpdatedHandler extends EventHandler {
		void onTopsUpdated(Top10Lists tops);
	}

	Top10Lists tops;

	public TopsUpdatedEvent(Top10Lists tops) {
		this.tops = tops;
	}

	public static final GwtEvent.Type<TopsUpdatedEvent.TopsUpdatedHandler> TYPE = new GwtEvent.Type<TopsUpdatedHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TopsUpdatedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopsUpdatedEvent.TopsUpdatedHandler handler) {
		handler.onTopsUpdated(tops);
	}

}
