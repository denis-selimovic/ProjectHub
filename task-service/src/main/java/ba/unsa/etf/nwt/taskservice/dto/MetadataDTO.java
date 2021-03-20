package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.response.interfaces.Metadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDTO implements Metadata {
    int pageNumber;
    long totalElements;
    int pageSize;
    boolean hasNext;
    boolean hasPrevious;

    public MetadataDTO(Page<?> page) {
        this.pageNumber = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getContent().size();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }
}
