package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Tag extends Model implements Comparable<Tag>{

	public String name;
	
	public Tag(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
	@Override
	public int compareTo(Tag otherTag) {
		return this.name.compareToIgnoreCase(otherTag.name);
	}
	
	public static Tag findOrCreateByName(String name){
		Tag tag = Tag.find("byName", name).first();
		if(tag == null){
			tag = new Tag(name);
		}
		return tag;
	}
}
