package org.auca.webtech.spms.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"EVENT_ID", "START_DATE","END_DATE"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PerformanceTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 321037963621657571L;

	@Id
	@Column(name = "performance_id",unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@JoinColumn(name = "EVENT_ID")
	@ManyToOne
	private Event event;
	
	@JoinColumn(name = "ARTIST_ID")
	@ManyToOne
	private Artist artist;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	private Boolean performed= Boolean.FALSE;
}
