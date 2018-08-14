/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */

package com.jaspersoft.commons.semantic.dsimpl;

import com.jaspersoft.commons.datarator.AbstractDataSetIterator;
import com.jaspersoft.commons.datarator.ColumnInfo;
import com.jaspersoft.commons.datarator.jr.AdHocObjectFactory;
import com.jaspersoft.commons.datarator.jr.JRDataSourceFactory;
import com.jaspersoft.commons.datarator.jr.QueryInterceptor;
import com.jaspersoft.commons.dataset.DataSet;
import com.jaspersoft.commons.dataset.DataSetException;
import com.jaspersoft.commons.dataset.DataSetIterator;
import com.jaspersoft.commons.dataset.expr.TypeUtil;
import com.jaspersoft.commons.xml.EasyXML;
import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.domain.impl.ExecutionContextImpl;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.RepositoryContextHandle;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.RepositoryContextManager;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.TibcoDriverManagerImpl;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.JRTimezoneJdbcQueryExecuterFactory;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.RepositoryContext;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.RepositoryUtil;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.repo.RepositoryURLHandlerFactory;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceContainer;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataAdapterService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;
import com.tibco.jaspersoft.cs.lucent.server.api.LucentGlobalContext;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRQueryExecuterUtils;
import net.sf.jasperreports.engine.util.LocalJasperReportsContext;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.*;

/**
 * this is a DataSet impl based on a JRQuery. When you run it, it gets a JRQueryExecuterFactory using
 * the usual JR mechanism and runs it to get a JRDataSource.
 * @author bob
 *
 */
public class JRQueryDataSet extends AbstractSemanticLayerDataSet implements SourceQueryDataSet {

	public static final String DATA_SET_TYPE = "jrQueryDataSet";
    public static final String PARAMS = "params";
    public static final String QUERY_LANGUAGE = "queryLanguage";
    private static final Logger log = Logger.getLogger(JRQueryDataSet.class);
    private JRDataSource jrds;
    private String query;
    private String queryLanguage;
    private Map<String, JRParameter> queryParameters;
    private JRQueryExecuter queryExecuter;
    private List<JRField> jrFields = new ArrayList<JRField>();
    private boolean iterated;
    private JRFieldMapper jrFieldMapper;
    private Map dataSourceParameters = null;
    private QueryInterceptor queryInterceptor;
    private DBServerConfigFactory dbConfigFactory;
    private String dataAdapter;
    private String dsReferenceURI;
    private RepositoryContextManager repositoryContextManager;
    private List<ParameterContributor> parameterContributors;

    @EasyXML.MapToAttribute
    public String getQueryLanguage() {
        return queryLanguage;
    }

    public void setQueryLanguage(String queryLanguage) {
        this.queryLanguage = queryLanguage;
    }

    @EasyXML.MapToElement
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @EasyXML.MapToElement
    public String getDataAdapter() {
        return dataAdapter;
    }

    public void setDataAdapter(String dataAdapter) {
        this.dataAdapter = dataAdapter;
    }

    @EasyXML.MapToElement
    public String getDsReferenceURI() {
        return dsReferenceURI;
    }

    public void setDsReferenceURI(String dsReferenceURI) {
        this.dsReferenceURI = dsReferenceURI;
    }

    public RepositoryContextManager getRepositoryContextManager() {
        return repositoryContextManager;
    }

    public void setRepositoryContextManager(RepositoryContextManager repositoryContextManager) {
        if (repositoryContextManager != null) this.repositoryContextManager = repositoryContextManager;
    }

    /**
     * convert this to a JRDataset for use by the query executer
     * TODO be able to define parameters
     * @return
     * @throws JRException 
     */
    protected JRDataset getJRDataset() throws JRException {
        JRDesignDataset jrds = new JRDesignDataset(false);
        // set query if present
        if (getQuery() != null) {
            JRDesignQuery jrquery = new JRDesignQuery();
            jrquery.setText(getQuery());
            jrquery.setLanguage(getQueryLanguage());
            jrds.setQuery(jrquery);
        }
        if (getParameterValues() != null) {
            Iterator pi = getParameterValues().keySet().iterator();
            while (pi.hasNext()) {
                String key = (String) pi.next();
                if (jrds.getParametersMap().containsKey(key)) {
                    continue;
                }
                Object value = getParameterValues().get(key);
                JRDesignParameter p = new JRDesignParameter();
                p.setName(key);
                Class<?> parameterClass = value != null ? value.getClass() : getParameterType(key);
                if (parameterClass != null) {
                    p.setValueClass(parameterClass);
                }
                jrds.addParameter(p);
            }
        }

        // add fields
        for (JRField f : getJRFieldList()) {
            try {
                jrds.addField(f);
            } catch (JRException e) {
                throw new IllegalStateException("unexpected exception adding field", e);
            }
        }
        // turn it into a base dataset
        return new AdHocObjectFactory().getDataset(jrds);
    }
    /**
     * Supply the JRDatasource that is used by our DataSetIterator.
     * If we didn't init the datasource yet, we have to:
     * - get a ReportDataSourceService from our resolver
     * - get the params that it supplies
     * - if one of the params is the JRDatasource, we're done
     * - otherwise get a query executer factory
     * - create a JRDataset that it needs to create a QE
     * - get the QE from the QEF
     * - get the JRDatasource from the QE
     * This gets called once we are actually iterating.
     * I learned to my dismay that the act of creating the datasource actually runs the query, 
     * so put this off until you actually need data! 
     * @return
     * @throws JRException 
     */
    private JRDataSource getJRDataSource() throws Exception {
        if (jrds == null) {
        	// adjust max value
        	if (getJdbcConnection() != null) {
	        	int maxMaxRows = dbConfigFactory.getConfig(getJdbcConnection()).getIntegerProperty(DBServerConfig.MAX_MAX_ROWS);
	        	if (maxMaxRows > 0 && getMaxRows() > maxMaxRows) {
	        		setMaxRows(maxMaxRows);
	        	}
        	}

            ReportDataSourceService dataSourceService = getDataSourceService();
            RepositoryContextHandle repositoryContextHandle = null;
            try {
                if (dataSourceService instanceof ReportDataAdapterService) {
                    repositoryContextHandle = setupThreadRepositoryContext(((ReportDataAdapterService)dataSourceService).getSourceFileOrganizationUri());
                }

                // get the full param map (side effect is to add data source params to initial param map)
                Map runningParams = getRuntimeAndDataSourceParameters(dataSourceService);
                // what if there is no query and the JasperServer data source puts it right in the param map?
                // this can be the case for custom datasources
                String query = getQuery();
                if ((query == null || query.equals("")) && (runningParams.get(JRParameter.REPORT_DATA_SOURCE) != null)) {
                    jrds = (JRDataSource) runningParams.get(JRParameter.REPORT_DATA_SOURCE);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("running JR query for SL: language = " + getQueryLanguage() + ", query = " + getQuery());
                    }

                    // then construct a JRDataset...
                    JRDataset jrDataset = getJRDataset();


                    // apply data adapter if it exists


                    if (dataAdapter != null) {
                        // set data adapter proptery in jrDataSet
                        /**
                         *  When creating AdhocTopicMetaData, get data adapter property, "net.sf.jasperreports.data.adapter",
                         *  from JRReport.dataset and add them into AdhocTopicMetaData
                         *  add to JRQueryDataSet in NewClassDataStrategy.getBaseQueryDataSet()
                         *  add to JRDataSet in JRQueryDataSet.getJRDataSet()
                         *  data adapters are external to query executers.  In JR, data adapters are called from JRFillDataset,
                         *  see the contributeParameters, getParameterContributors and disposeParameterContributors methods.
                         *  do a similar work as JRFillDataset in JRQueryDataSet to handle data adapter topic
                         *  find a way to retrieve resources from JRS repo
                         **/
                        jrDataset.getPropertiesMap().setProperty("net.sf.jasperreports.data.adapter", dataAdapter);
                        if (repositoryContextHandle == null) repositoryContextHandle = setupThreadRepositoryContext();

                        // setup data adapter
                        contributeParameters(runningParams, jrDataset);
                        // something report data source is ready to use after running data adapter.  No need to call query executer
                        if (runningParams.get(JRParameter.REPORT_DATA_SOURCE) != null) {
                            jrds = (JRDataSource) runningParams.get(JRParameter.REPORT_DATA_SOURCE);
                            return jrds;
                        }
                    }

                    // if we do have a query, we need to get a query executer and ask it for a datasource
                    // first look up the query executer factory...
                    QueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getInstance(getJasperReportsContext()).getExecuterFactory(getQueryLanguage());

                    // if we have an interceptor hook, call it

                    if ((queryInterceptor != null) && ("sql".equalsIgnoreCase(getQueryLanguage()))) {
                        QueryInterceptor qiInstance = (QueryInterceptor) queryInterceptor.clone();
                        qiInstance.setDataset(jrDataset);
                        qiInstance.setParams(runningParams);
                        qiInstance.beforeQuery();
                    }

                    // convert your param map to fill params...
                    Map<String, ? extends JRValueParameter>  fillParams = JRDataSourceFactory.convertToFillParameters(jrDataset, runningParams);
                    fillParams = JRDataSourceFactory.addToFillParameters(fillParams, queryExecuterFactory.getBuiltinParameters(), runningParams);
                    // and pass it all to the factory to get the queryExecuter

                    queryExecuter = queryExecuterFactory.createQueryExecuter(getJasperReportsContext(), jrDataset, fillParams);

                    // ok, now we're in the innermost Russian doll...let's get our JRDatasource!
                    
                    //jsw.start
                    //jrds = queryExecuter.createDatasource();
        			jrds = LucentGlobalContext.getInstance().wrapCreateJRDataSource(queryExecuter);
        			//jsw.end.
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            } finally {
                resetThreadRepositoryContext(repositoryContextHandle);
            }
        }
        return jrds;
    }
    
    public Map getRuntimeAndDataSourceParameters() {
        return getRuntimeAndDataSourceParameters(getDataSourceService());
    }
    /**
     * It's time to play our favorite game, "guess what's in the parameter bag"!
     * 
     * lazy init the data source params by getting the data source and calling getDataSourceService().
     * This is used when we actually run the query, and it's also used by SQLGenerator to figure out
     * what kind of db we have.
     * FIXME the datasource params are lazy-initted because calling setReportParameterValues() can 
     * create a connection, and we are calling this multiple times just to get the query string,
     * without actually running a query.
     * Sometimes the parameter values are set differently between various uses.
     * Usually this is OK, but if the dataSourceService depends on particular parameters being there,
     * then we would need to make the access more uniform.
     * @return
     */
    public Map getRuntimeAndDataSourceParameters(ReportDataSourceService dataSourceService) {
        // init dataSourceParameters if not done already for this dataset
    	synchronized(this) {
    		if (dataSourceParameters == null) {
	            dataSourceParameters = new LinkedHashMap(getParameterValues());
	            if (dataSourceService instanceof ReportDataAdapterService) {
	                dataSourceParameters.put("JRQueryDataSet_JasperReportsContext", getJasperReportsContext());
	            }
	            try {
	                dataSourceService.setReportParameterValues(dataSourceParameters);
	            } finally {
	                dataSourceParameters.remove("JRQueryDataSet_JasperReportsContext");
	            }
	            // only save params added by setReportParameterValues()
	            for (String pname : getParameterValues().keySet()) {
		            dataSourceParameters.remove(pname);
	            }
	            if (log.isDebugEnabled()) {
	            	log.debug("opened connection " + getJdbcConnection());
	            }
        	}
        }
    	// Bug 42799 and others; params from datasource should be set after whatever params got passed in to dataset;
    	// when this code is being run inside the domain query executer, all the possible JR params (including datasource params like REPORT_CONNECTION) are set, 
    	// but they may have null values, so the params from the datasource should have precedence.
    	// 
        Map allParameters = new LinkedHashMap(getParameterValues());
        allParameters.putAll(dataSourceParameters);
        // handle maxRows by putting it in the param map
        // we are going to assume that if this param is already set, we are getting called in QE and the user wants it set
        Integer maxRowsParam = (Integer) allParameters.get(JRParameter.REPORT_MAX_COUNT);
        if (maxRowsParam == null) {
        	// otherwise set it
        	allParameters.put(JRParameter.REPORT_MAX_COUNT, getMaxRows());
        }
        return allParameters;
    }
    
    /**
     * convenience method for JdbcBaseDataSet to get the connection
     * @return
     */
    public Connection getJdbcConnection() {
    	// some non-sql query languages still use a REPORT_CONNECTION...don't try to use this for anything
		if (! "sql".equalsIgnoreCase(queryLanguage)) {
			return null;
		}
        Connection jdbcConnection = (Connection) getRuntimeAndDataSourceParameters().get(JRParameter.REPORT_CONNECTION);
        try {
            TibcoDriverManagerImpl.getInstance().unlockDSConnection(jdbcConnection);
       } catch (Exception ex) {
            ex.printStackTrace();
    }
        return jdbcConnection;
    }	
	
	
    public TimeZone getDadaSetTimeZone() {
    	// some non-sql query languages still use a REPORT_CONNECTION...don't try to use this for anything
		if (! "sql".equalsIgnoreCase(queryLanguage)) {
			return null;
		}
        return (TimeZone) getRuntimeAndDataSourceParameters().get(JRTimezoneJdbcQueryExecuterFactory.PARAMETER_TIMEZONE);
    }

    private List<JRField> getJRFieldList() {
        if (jrFields == null || jrFields.size() < getColumnCount()) {
            jrFields = getJRFieldMapper().getJRFieldList(getFieldList());
        }
        return jrFields;
    }
    
    private JRField getJRField(int index) {
        if (index < 0 || index >= getJRFieldList().size()) {
            throw new IllegalArgumentException("field index " + index + " is out of bounds (there are " + getJRFieldList().size() + " fields)");
        }
        return getJRFieldList().get(index);
    }

    public interface JRFieldMapper {
        List<JRField> getJRFieldList(List<ColumnInfo> fieldList);
    }
    
    public void setJRFieldMapper(JRFieldMapper jrFieldMapper) {
        this.jrFieldMapper = jrFieldMapper;
    }
    
    /**
     * Overrideable method for turning SemanticLayerFields into JRFields.
     * The default way is to call ColumnInfo.toJRField(), but we need to expose
     * a way for the mappings to result set field names to be tweaked.
     * Two examples:
     * Oracle (and others) limit identifiers to 30 characters.
     * JR doesn't work well with using "." in identifiers on JDBC queries.
     * @return
     */
    public JRFieldMapper getJRFieldMapper() {
        if (jrFieldMapper == null) {
            return new JRFieldMapper() {
                public List<JRField> getJRFieldList(List<ColumnInfo> fieldList) {
                    List<JRField> jrFieldList = new ArrayList<JRField>();
                    for (ColumnInfo c : getFieldList()) {
                        jrFieldList.add(c.toJRField());
                    }
                    return jrFieldList;                }
                };
        }
        return jrFieldMapper;
    }
    
    public DataSetIterator getIterator() {
        if (iterated) {
            throw new IllegalStateException("getIterator() has already been called once");
        }
        iterated = true;
        return new JRDataSetIterator();
    }
    
    /**
     * this iterator wraps the underlying JRDatasource
     * @author bob
     *
     */
    private class JRDataSetIterator extends AbstractDataSetIterator {
        private int rows;

        protected DataSet getDataSet() {
        	return JRQueryDataSet.this;
        }
        
        public boolean next() throws DataSetException {
            try {
                if (rows >= getMaxRows()) {
                    // we're done
                    close();
                    return false;
                }
                rows++;
                return getJRDataSource().next();
            } catch (JRRuntimeException jrrte) {
            	// bug 18608
            	// this error will occur if you have a null value for a param that is used
            	// in a $P{} expression (or the like) in a topic.
            	// instead of blowing up, return empty result set
            	log.warn("exception caught getting JRDatasource: " + jrrte.getMessage());
            	return false;
            } catch (Exception e) {
                throw new DataSetException("Exception calling JRDataSource.next() for query " + query, e);
            }
        }

        public Object getObject(String name) throws DataSetException {
            int index = getColumnIndex(name);
            return getObject(index);
        }

        public Object getObject(int index) throws DataSetException {
            try {
            	SemanticLayerField column = (SemanticLayerField) getColumnInfoList().get(index);
            	
            	Object result = getJRDataSource().getFieldValue(getJRField(index));
            	if (column.isAvailableField()) {
            		return result;
            	} else {
            	    // we're using a method to get "default" values for different types,
            	    // but it's returning the "zero" time and we want "now"
            	    Object defaultValue = TypeUtil.getDefaultValue(getJRField(index).getValueClassName());
            	    if (defaultValue instanceof Date) {
            	        Date defaultDate = (Date) defaultValue;
            	        defaultDate = (Date) defaultDate.clone();
            	        defaultDate.setTime(System.currentTimeMillis());
            	        return defaultDate;
            	    } else {
            	        return defaultValue;
            	    }
            	}
            } catch (Exception e) {
                throw new DataSetException("Exception calling JRDataSource.next() for query " + query, e);
            }
        }

        public void close() {
            if (queryExecuter != null) {
                queryExecuter.close();
            }
            // clean up parameter contributors
            disposeParameterContributors();
        }

        public void cancel() {
            if (queryExecuter != null) {
                try {
                	queryExecuter.cancelQuery();
				} catch (JRException e) {
					log.warn("exception canceling query", e);
				}
            }
            // clean up parameter contributors
            disposeParameterContributors();
        }
    }

    public String getDatasetKey() {
        return getQuery() == null ? "" : getQuery();
    }
    
    public Object getCacheKey() {
        if (cacheKey == null) {
            // call super to init
            super.getCacheKey();
            // add queryLanguage
            if (getQueryLanguage() != null) {
                cacheKey.put(QUERY_LANGUAGE, getQueryLanguage());
            }
        }
        return cacheKey;
    }
 

    protected Map<String, Object> getCacheParameterValues() {
    	Map<String, Object> cacheParamValues = new HashMap<String, Object>();
    	for (String paramName : getParameterValues().keySet()) {
            // we need to include pre-defined parameter for parameterized topic in cache key
    		if (includedParams.contains(paramName) || isQueryParameter(paramName)) {
    			cacheParamValues.put(paramName, getParameterValues().get(paramName));
        	} else if (getQueryParameters() != null) {
    			JRParameter jrParam = getQueryParameters().get(paramName);
    			if (jrParam != null && ! jrParam.isSystemDefined()) {
    				cacheParamValues.put(paramName, getParameterValues().get(paramName));
    			}
    		}
    	}
    	return cacheParamValues;
    }

    // is it pre-defined parameter for parameterized topic
    private boolean isQueryParameter(String paramName) {
        return (query != null) && (query.indexOf("$P{" + paramName + "}") >= 0);
    }

    protected void copyQueryInfo(AbstractSemanticLayerDataSet newds) {
        JRQueryDataSet newqds = (JRQueryDataSet) newds;
        super.copyQueryInfo(newds);
        newqds.setQuery(getQuery());
        newqds.setQueryLanguage(getQueryLanguage());
        newqds.setQueryParameters(getQueryParameters());
        newqds.setDataAdapter(getDataAdapter());
        newqds.setDsReferenceURI(getDsReferenceURI());
        newqds.setRepositoryContextManager(getRepositoryContextManager());
    }

	public List<String> validate() {
        List<String> errors = super.validate();
        if (query == null) {
            errors.add("JRQueryDataSet has no query");
        }
        if (queryLanguage == null) {
            errors.add("JRQueryDataSet has no queryLanguage");
        }
        return errors;
    }

    public String getDataSetType() {
        return DATA_SET_TYPE;
    }

	public QueryInterceptor getQueryInterceptor() {
		return queryInterceptor;
	}

	public void setQueryInterceptor(QueryInterceptor interceptor) {
		this.queryInterceptor = interceptor;
	}

	public DBServerConfigFactory getDbConfigFactory() {
		return dbConfigFactory;
	}

	public void setDbConfigFactory(DBServerConfigFactory dbConfigFactory) {
		this.dbConfigFactory = dbConfigFactory;
	}

    public Map<String, JRParameter> getQueryParameters() {
		return queryParameters;
	}

    public void setQueryParameters(Map<String, JRParameter> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public void contributeParameters(Map<String,Object> parameterValues, JRDataset jrDataset) throws JRException
	{
		parameterContributors = getParameterContributors(new ParameterContributorContext(getJasperReportsContext(), jrDataset, parameterValues));
		if (parameterContributors != null)
		{
			for(ParameterContributor contributor : parameterContributors)
			{
				contributor.contributeParameters(parameterValues);
			}
		}
	}

	private List<ParameterContributor> getParameterContributors(ParameterContributorContext context) throws JRException
	{
		List<ParameterContributor> allContributors = null;
		List<?> factories = getJasperReportsContext().getExtensions(ParameterContributorFactory.class);
		if (factories != null && factories.size() > 0)
		{
			allContributors = new ArrayList<ParameterContributor>();
			for (Iterator<?> it = factories.iterator(); it.hasNext();)
			{
				ParameterContributorFactory factory = (ParameterContributorFactory)it.next();
				List<ParameterContributor> contributors = factory.getContributors(context);
				if (contributors != null)
				{
					allContributors.addAll(contributors);
				}
			}
		}
		return allContributors;
	}

	/**
	 * clean up ParameterContributors (used for a data adapter if present) on close of iterator
	 */
    public void disposeParameterContributors()
    {
        if (parameterContributors != null)
        {
            for(ParameterContributor contributor : parameterContributors)
            {
                contributor.dispose();
            }
        }
    }

    protected LocalJasperReportsContext getJasperReportsContext() {
        LocalJasperReportsContext jasperReportsContext = new LocalJasperReportsContext(DefaultJasperReportsContext.getInstance());
        jasperReportsContext.setURLStreamHandlerFactory(RepositoryURLHandlerFactory.getInstance());
		return jasperReportsContext;
    }

        /*
     *  setup repository context in order to look up resources from repo
     */
    protected RepositoryContextHandle setupThreadRepositoryContext() {
      return setupThreadRepositoryContext(dsReferenceURI);
    }

    protected RepositoryContextHandle setupThreadRepositoryContext(String organizationUri) {
        RepositoryContext repositoryContext = RepositoryUtil.getThreadRepositoryContext();
        log.debug("JRQueryDataSet topic uri = " + organizationUri);
        log.debug("REFERENCE URI - " + organizationUri);
        log.debug("REPOSITORY CONTEXT = " + (repositoryContext != null) );
        log.debug("repositoryContextManager CONTEXT = " + (repositoryContextManager != null) );
        if (repositoryContextManager != null && organizationUri != null) {
            log.debug("set Repository context");
            final ExecutionContext executionContext;
            final ResourceContainer reportUnit;
            if(repositoryContext != null) {
                executionContext = repositoryContext.getExecutionContext();
                reportUnit = repositoryContext.getReportUnit();
            } else {
                executionContext = ExecutionContextImpl.getRuntimeExecutionContext();
                reportUnit = null;
            }
            return repositoryContextManager.setRepositoryContext(executionContext, organizationUri, reportUnit);
        } else {
            log.debug("JRQueryDataSet - setThreadRepositoryContext fail " + (repositoryContext != null) + ", " + (repositoryContextManager != null));
            return null;
        }
    }

    /*
     *  reset repository context to back to previous repository context
     */
    protected void resetThreadRepositoryContext(RepositoryContextHandle repositoryContextHandle) {
        if (repositoryContextHandle != null) {
            repositoryContextHandle.unset();
        }
    }
}
