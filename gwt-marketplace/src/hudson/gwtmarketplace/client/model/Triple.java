/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;


public class Triple<Entity1, Entity2, Entity3> extends Pair<Entity1, Entity2> {
	private static final long serialVersionUID = 1L;

	private Entity3 entity3;

	public Triple() {
		super();
	}

	public Triple(Entity1 entity1, Entity2 entity2, Entity3 entity3) {
		super(entity1, entity2);
		this.entity3 = entity3;
	}
	
	public Entity3 getEntity3() {
		return entity3;
	}
	public void setEntity3(Entity3 entity3) {
		this.entity3 = entity3;
	}
}
