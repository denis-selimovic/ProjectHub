package ba.unsa.etf.nwt.taskservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    public enum SearchCriteriaOperation { EQ, NEQ, LTE, GTE, LIKE }

    private String key;
    private String value;
    private SearchCriteriaOperation operation;
}
