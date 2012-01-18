/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.event;

import java.util.Date;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TopsDateCheckEvent extends
		GwtEvent<TopsDateCheckEvent.TopsDateCheckHandler> {

	public interface TopsDateCheckHandler extends EventHandler {
		void onTopsDateCheck(Date date);
	}

	Date date;

	public TopsDateCheckEvent(Date date) {
		this.date = date;
	}

	public static final GwtEvent.Type<TopsDateCheckEvent.TopsDateCheckHandler> TYPE = new GwtEvent.Type<TopsDateCheckHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TopsDateCheckHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopsDateCheckEvent.TopsDateCheckHandler handler) {
		handler.onTopsDateCheck(date);
	}

}
