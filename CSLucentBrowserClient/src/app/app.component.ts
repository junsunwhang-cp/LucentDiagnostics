import { Component, OnInit } from '@angular/core';
import { DataService} from './services/data.service';
import { resource } from 'selenium-webdriver/http';

interface ServerInfo {
  label ?:string,
  domain ?:string,
  port ?: number,
  path ?: string,
  username ?:string,
  password ?:string,
  notes ?:string,
  reports ?:ReportInfo[]
  expanded ?:boolean
}

interface ReportInfo {
  selected ?:boolean,
  name ?:string,
  url ?:string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'app';
  servers:ServerInfo[] = [];
  workingServerLabel:string;

  constructor(private dataService: DataService) {
  }

  ngOnInit(){ //test intitialization of some server entries.
    let r1:ReportInfo = {
      selected:true, name:'first Report', url:"repo://test/report1" 
    }

    let r2:ReportInfo = {
      selected:true, name:'second Report', url:"repo://test/report2"
    }

    let testReports:ReportInfo[] = [r1, r2];
    
    let s1:ServerInfo = {
      label:'first test server label',
      domain:'localhost',
      port: 8080,
      path: 'jasperserver-pro',
      username:'superuser',
      password:'superuser', 
      notes:'some random notes',
      reports:testReports,
      expanded:false };

      let s2:ServerInfo = {
        label:'second test server label',
        domain:'localhost',
        port: 8080,
        path: 'jasperserver-pro',
        username:'superuser',
        password:'superuser',
        notes:'some random notes for server 2',
        reports:testReports,
        expanded:false };
  
    this.servers.push(s1);
    this.servers.push(s2);
  }

  onUpdateMeta(){
    console.log("persist meta data");
  }

  addServerMeta(serverInfo){
    //console.log(serverInfo);
    let srs:ReportInfo[] = [];

    let ss:ServerInfo = {
      label:serverInfo,
      domain:'',
      port: 8080,
      path: 'jasperserver-pro',
      username:'',
      password:'',
      notes:'',
      reports:srs,
      expanded:true };

    this.servers.unshift(ss);

    return false;
  }

  deleteServerInfo(server){
    for(let i=0;i<this.servers.length;i++){
      if(this.servers[i]==server){
        this.servers.splice(i,1);
      }
    }
  }

  addServerDetail(server,domain,port,path,notes){
    //update details and try to update the list of reports.
    //console.log(domain + port + path);
    server.domain = domain;
    server.port = port;
    server.path = path;
    server.notes = notes;
  }

  testDataFetch(){
    console.log("test data fetch:");
    // var objOut:any = this.dataService.getRepoReports();
    // var stringOut:string = JSON.stringify(objOut);
    this.dataService.getRepoReports().subscribe((resource) => {console.log(resource); });

    //console.log(stringOut);
  }

}


