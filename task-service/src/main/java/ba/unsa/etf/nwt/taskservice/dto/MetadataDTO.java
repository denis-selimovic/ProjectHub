package ba.unsa.etf.nwt.taskservice.response.base;

import ba.unsa.etf.nwt.taskservice.response.interfaces.Metadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataResponse implements Metadata {
    @JsonProperty("page_number")
    int pageNumber;

    @JsonProperty("total_available")
    long totalElements;

    @JsonProperty("page_size")
    int size;

    @JsonProperty("has_next")
    boolean hasNext;

    @JsonProperty("has_previous")
    boolean hasPrevious;

    public MetadataResponse(Page<?> page) {
        this.pageNumber = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.size = page.getContent().size();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }
}
