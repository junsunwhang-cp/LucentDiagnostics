import { Injectable, Component, Inject } from '@angular/core';
import { Http, Headers } from '@angular/http';

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

  getRepoReports(hostName:string, port:string, path:string, username:string, password:string){
 
    var reportResults = this.http.get('/serverinfo?hostname=' + hostName + 
    '&port=' + port + '&path=' + path + '&username=' + username + '&password=' + password)
    .map(res => res.json());

    return reportResults;
 
  }

  execLoadTest(server:ServerInfo){
    var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('LoadTestId', 'testId1_jswTest');
    return this.http.post('/loadtest/start', server, {headers}).map(res => res.json());
  }

} 




