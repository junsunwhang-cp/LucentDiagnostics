import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
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

  getRepoReports(){
    return this.http.get('http://localhost:8080/jasperserver-pro/rest_v2/resources?type=reportUnit&j_username=superuser&j_password=superuser')
    .map(res => res.json());
  }
}
