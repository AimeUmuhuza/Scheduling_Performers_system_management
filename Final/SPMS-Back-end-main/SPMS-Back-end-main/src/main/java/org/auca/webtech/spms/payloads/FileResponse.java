package org.auca.webtech.spms.payloads;

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
public class FileResponse {
	private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
}
