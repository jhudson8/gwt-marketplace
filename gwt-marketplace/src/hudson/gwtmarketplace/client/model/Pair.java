/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.io.Serializable;

public class Pair<Entity1, Entity2> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Entity1 entity1;
	private Entity2 entity2;
	
	public Pair() {}
	
	public Pair(Entity1 entity1, Entity2 entity2) {
		this.entity1 = entity1;
		this.entity2 = entity2;
	}
	
	public Entity1 getEntity1() {
		return entity1;
	}
	public void setEntity1(Entity1 entity1) {
		this.entity1 = entity1;
	}
	public Entity2 getEntity2() {
		return entity2;
	}
	public void setEntity2(Entity2 entity2) {
		this.entity2 = entity2;
	}
}
