/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import com.google.gwt.user.client.Command;

public interface AsyncCommand<T> extends Command {

	public void preExecute();

	public void onSuccess(T result);

	public void onFailure(Throwable e);
}
