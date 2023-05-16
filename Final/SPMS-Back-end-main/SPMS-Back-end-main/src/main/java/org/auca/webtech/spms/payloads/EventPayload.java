package org.auca.webtech.spms.payloads;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class EventPayload {
	
	private UUID id;

	private String name;
	
	private Date date;
	
	private Date startDate;
	
	private Date endDate;
	
	private String location;
}
