package com.web.pet.global;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

import com.web.pet.pojo.annotations.Cotcolor;

public class LazyCotcolorSorter implements Comparator<Cotcolor> {

	private String sortField;
    private SortOrder sortOrder;
    
    public LazyCotcolorSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @SuppressWarnings("rawtypes")
	public int compare(Cotcolor car1, Cotcolor car2) {
        try {
            Object value1 = Cotcolor.class.getField(this.sortField).get(car1);
            Object value2 = Cotcolor.class.getField(this.sortField).get(car2);

            @SuppressWarnings("unchecked")
			int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
