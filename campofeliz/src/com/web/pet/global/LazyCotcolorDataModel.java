package com.web.pet.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.pojo.annotations.Cotcolor;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyCotcolorDataModel extends LazyDataModel<Cotcolor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7703350134948794852L;
	private List<Cotcolor> datasource;
	    
    public LazyCotcolorDataModel(List<Cotcolor> datasource) {
        this.datasource = datasource;
    }
	    
    @Override
    public Cotcolor getRowData(String rowKey) {
        for(Cotcolor cotcolor : datasource) {
            if(cotcolor.getIdcolor() == Integer.parseInt(rowKey))
                return cotcolor;
        }

        return null;
    }

    @Override
    public Object getRowKey(Cotcolor cotcolor) {
        return cotcolor.getIdcolor();
    }

    @Override
    public List<Cotcolor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
        List<Cotcolor> data = new ArrayList<Cotcolor>();

        //filter
        for(Cotcolor cotcolor : datasource) {
            boolean match = true;

            for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                try {
                    String filterProperty = it.next();
                    String filterValue = filters.get(filterProperty);
                    String fieldValue = String.valueOf(cotcolor.getClass().getField(filterProperty).get(cotcolor));

                    if(filterValue == null || fieldValue.startsWith(filterValue)) {
                        match = true;
                    }
                    else {
                        match = false;
                        break;
                    }
                } catch(Exception e) {
                    match = false;
                } 
            }

            if(match) {
                data.add(cotcolor);
            }
        }

        //sort
        if(sortField != null) {
            Collections.sort(data, new LazyCotcolorSorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
