import { Injectable, Component, Inject } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { v4 as uuid } from 'uuid';

import { ServerInfo, ReportInfo, DiagnosticsInfo } from '../comms.service';
import 'rxjs/add/operator/map';

@Injectable()
export class DataService {

  constructor(public http:Http) {
    console.log('ds connect.')  ;
  }

  //jsw: test only. TODO: remove.
  getPosts(){   
    return this.http.get('https://jsonplaceholder.typicode.com/posts')
      .map(res => res.json());
  }
  //jsw.test.end.

  getServerMeta(){
    var serverMeta = this.http.get('/serverinfo/meta').map(sMeta => sMeta.json());
    return serverMeta;
  }

  getRepoReports(id:string, hostName:string, port:string, path:string, username:string, 
    password:string, label:string, notes:string ){
    var reportResults = this.http.get(encodeURI('/serverinfo/reportList?' + 'id=' + id + '&hostname=' + hostName + 
    '&port=' + port + '&path=' + path + '&username=' + username + '&password=' + password +
    '&label=' + label + '&notes=' + notes)
  ).map(res => res.json());
    return reportResults;
  }

  execLoadTest(server:ServerInfo){
    var headers = new Headers();
    var newTestId = uuid();
    server.testId = newTestId;
    headers.append('Content-Type', 'application/json');
    headers.append('lucent_test_id', newTestId);
    //headers.append('LoadTestId', 'testId1_jswTest');
    return this.http.post('/loadtest/start', server, {headers}).map(res => res.json());
  }

  execStatusCheck(server:ServerInfo){
    var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        //headers.append('LoadTestId', 'testId1_jswTest');
        headers.append('lucent_test_id', server.testId);
    return this.http.post('/loadtest/status', server, {headers}).map(res => res.json());
  }

  viewReportResults(server:ServerInfo){
    return this.http.get('/viewreport?lucent_test_id=' + server.testId + "&domain=" + server.domain + "&port=" + server.port);
  }

} 




