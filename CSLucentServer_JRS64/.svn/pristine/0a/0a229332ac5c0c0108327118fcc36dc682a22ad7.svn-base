/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */

package com.jaspersoft.jasperserver.api.engine.jasperreports.util;

import com.jaspersoft.commons.query.QueryValidationException;
import com.jaspersoft.jasperserver.api.JSExceptionWrapper;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Query;
import com.tibco.jaspersoft.cs.lucent.server.api.LucentGlobalContext;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRQueryExecuterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class JRDomainQueryExecuterAdapter extends JRQueryExecuterAdapter {
    
    private static final Log log = LogFactory.getLog(JRDomainQueryExecuterAdapter.class);
    
    public static Map executeQuery(final Query query, final String[] resultColumns, final String[] types,  Map parameterValues) throws ClassNotFoundException {
        try {
            QueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getInstance(DefaultJasperReportsContext.getInstance()).getExecuterFactory(query.getLanguage());
            
            JRParameter[] dsParameters = getDatasetParameters(queryExecuterFactory, 
            		parameterValues, null);

            JRField[] fields = getDatasetFields(resultColumns, types);

            JRQuery dsQuery = makeReportQuery(query);
            JSDataset dataset = new JSDataset(query.getName(), dsParameters, fields, dsQuery);
            
            Map parametersMap = new HashMap();
            for (int i = 0; i < dsParameters.length; i++) {
                JRParameter parameter = dsParameters[i];
                parametersMap.put(parameter.getName(), parameter);
            }
            
            JRQueryExecuter executer = queryExecuterFactory.createQueryExecuter(dataset, parametersMap);
            try {
            	//jsw.start
                //JRDataSource ds = executer.createDatasource();
    			JRDataSource ds = LucentGlobalContext.getInstance().wrapCreateJRDataSource(executer);
    			//jsw.end.
    			
                Map columnValues = new LinkedHashMap();
                while (ds.next()) {
                    for (int idx = 0; idx < resultColumns.length; ++idx) {
                        List oneColValues = (List) columnValues.get(resultColumns[idx]);
                        if (oneColValues == null) {
                            oneColValues = new ArrayList();
                            columnValues.put(resultColumns[idx], oneColValues);
                        }
                        
                        oneColValues.add(ds.getFieldValue(dataset.getField(resultColumns[idx])));
                    }

                }
                
                return columnValues;
            } finally {
                executer.close();
            }
        } catch (QueryValidationException qve) {
            throw qve;
        } catch (JRException e) {
            log.error("Error while executing query", e);
            throw new JSExceptionWrapper(e);
        }

    }
    
    protected static JRField[] getDatasetFields(final String[] resultColumns, final String[] types) throws ClassNotFoundException {

        JRField[] fields = new JRField[resultColumns.length];
        for (int idx = 0; idx < resultColumns.length; ++idx) {
            if (types[idx] != null) {
                fields[idx] = new ColumnField(resultColumns[idx], Class.forName(types[idx]));
            }
            else {
                fields[idx] = new ColumnField(resultColumns[idx], Object.class);
            }
        }
        return fields;
    }

}
