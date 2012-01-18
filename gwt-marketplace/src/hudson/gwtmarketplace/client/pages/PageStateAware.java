/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages;

public interface PageStateAware {

	public enum Type {
		STANDARD, STACK_START, STACK_ADD, STANDARD_SECURE;
	
		public boolean isSecuire() {
			return this == STANDARD_SECURE;
		}
	}
	
	public void onShowPage(String[] parameters);
	
	public void onExitPage();

	public Type getPageType();
}
