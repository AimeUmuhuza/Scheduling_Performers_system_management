package org.auca.webtech.spms.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Admin extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = -511624464503255417L;

	public Admin() {
		super();
	}
	
}
