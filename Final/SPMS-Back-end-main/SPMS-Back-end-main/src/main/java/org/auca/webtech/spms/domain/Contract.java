package org.auca.webtech.spms.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Contract implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID",unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@JoinColumn(name = "USER_ID")
	@ManyToOne
	private Admin admin;
	
	@JoinColumn(name = "ARTIST_ID")
	@ManyToOne
	private Artist artist;
	
	@JoinColumn(name = "FILE_ID")
	@ManyToOne
	private FileEntity file;
	
	@Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
