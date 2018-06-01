/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.commons.datarator.jr;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.query.JRQueryExecuter;

import com.jaspersoft.commons.datarator.AbstractDataSet;
import com.jaspersoft.commons.datarator.AbstractDataSetIterator;
import com.jaspersoft.commons.dataset.DataSet;
import com.jaspersoft.commons.dataset.DataSetException;
import com.jaspersoft.commons.dataset.DataSetIterator;
import com.tibco.jaspersoft.cs.lucent.server.api.LucentGlobalContext;

/**
 * this wraps a JRDataSource instance inside a DataSet, and it's used
 * to map datasets into the cache system.
 * TODO a lot of this stuff is being done inside semantic layer's JRQueryDataSet
 * so we should probably just subclass that.
 * @author bob
 *
 */
public class JRDataSourceAdapter extends AbstractDataSet {
    public static final String PROP_EXPRESSION = "adhoc.expression";
    private JRDataSource ds;
    private JRDataset jrds;
    private JRQueryExecuter qe;
    private String queryText;
    private String queryLanguage;
    private JRField[] fields;
    private Map params;
    // only return an iterator once
    private boolean iterated = false;
	private QueryInterceptor queryInterceptor;
    
    /**
     * create from parts
     * @param jrds the data source
     * @param fields list of JRField objects
     * @param queryInterceptor 
     */
    public JRDataSourceAdapter(JRDataset jrds, Map params, QueryInterceptor queryInterceptor) {
        // set dataset, fields, and key (formed from params and query string)
    	this.jrds = jrds;
    	JRQuery q = jrds.getQuery();
    	if (q != null) {
    		queryText = q.getText();
    		queryLanguage = q.getLanguage();
    	}
        this.params = params;
        this.queryInterceptor = queryInterceptor;
        initFields(jrds.getFields());
    }
    
    private void initFields(JRField[] fields) {
        this.fields = fields;
        // create a column for each field
        for (int i = 0; i < fields.length; i++) {
            // don't include calculated fields
            JRPropertiesMap props = fields[i].getPropertiesMap();
            if (props != null && props.getProperty(PROP_EXPRESSION) != null) {
                continue;
            }
            addColumnInfo(fields[i].getName(), fields[i].getValueClassName());
        }
    }
    
    /**
     * lazy init the data source from the query executer
     * I learned to my dismay that the act of creating the datasource actually runs the query, so put this off until you actually need data!
     * @return
     * @throws JRException 
     */
    private JRDataSource getJRDataSource() throws JRException {
        if (ds == null) {
            // what if there is no query and the JasperServer data source puts the JRDataSource right in the param map?
            // this can be the case for custom datasources
            if (queryText == null) {
                ds = (JRDataSource) params.get(JRParameter.REPORT_DATA_SOURCE);
            } else {
            	// don't call interceptor until we need to call createDatasource
            	if (queryInterceptor != null) {
            		queryInterceptor.beforeQuery();
            	}
            	qe = JRDataSourceFactory.createJRQueryExecuter(jrds, params);
            	//jsw.start
                //ds = qe.createDatasource();
    			ds = LucentGlobalContext.getInstance().wrapCreateJRDataSource(qe);
    			//jsw.end.                
            }
        }
        return ds;
    }
    
    private JRField getJRField(int index) {
        if (index < 0 || index >= fields.length) {
            throw new IllegalArgumentException("field index " + index + " is out of bounds (there are " + fields.length + " fields)");
        }
        return fields[index];
    }

    public DataSetIterator getIterator() {
        if (iterated) {
            throw new IllegalStateException("getIterator() has already been called once");
        }
        iterated = true;
        return new JRDataSetIterator();
    }
    
    private class JRDataSetIterator extends AbstractDataSetIterator {
        private int rows;

        protected DataSet getDataSet() {
        	return JRDataSourceAdapter.this;
        }
        
        public boolean next() throws DataSetException {
            try {
                if (rows >= getMaxRows()) {
                    return false;
                }
                rows++;
                return getJRDataSource().next();
            } catch (JRException e) {
                throw new DataSetException("Exception calling JRDataSource.next()", e);
            }
        }

        //
        //  2012-05-25  thorick chow
        //              http://bugzilla.jaspersoft.com/show_bug.cgi?id=24307
        //
        //              Note:  Oracle JDBC will return NULL for SELECT ''  (select empty string)
        //
        public Object getObject(String name) throws DataSetException {
            int index = getColumnIndex(name);
            try {
                return getJRDataSource().getFieldValue(getJRField(index));
            } catch (JRException e) {
                throw new DataSetException("Exception calling JRDataSource.getFieldValue()", e);
            }
        }

        public Object getObject(int index) throws DataSetException {
            try {
                return getJRDataSource().getFieldValue(getJRField(index));
            } catch (JRException e) {
                throw new DataSetException("Exception calling JRDataSource.getFieldValue()", e);
            }
        }

        public void close() {
            if (qe != null) {
                qe.close();
            }
        }

        public void cancel() {
            if (qe != null) {
                try {
					qe.cancelQuery();
				} catch (JRException e) {
				}
            }
        }
    }

    public String getDatasetKey() {
        return queryText == null ? "" : queryText;
    }
    
    public Object getCacheKey() {
        if (cacheKey == null) {
            // call super to init
            super.getCacheKey();
            // add params, queryLanguage
            cacheKey.put(PARAMS, new HashMap(params));
            if (queryLanguage != null) {
                cacheKey.put(QUERY_LANGUAGE, queryLanguage);
            }
        }
        return cacheKey;
    }
}
