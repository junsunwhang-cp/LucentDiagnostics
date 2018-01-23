import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { DataService} from './services/data.service';
import { resource } from 'selenium-webdriver/http';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommsService } from './comms.service';
import { ServerInfo, ReportInfo, DiagnosticsInfo, FetchReportListPacket } from './comms.service';
import { v4 as uuid } from 'uuid';
import { Subscription } from 'rxjs';
import { Observable } from 'rxjs/Observable';

//jsw.test.start.
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
//jsw.test.end.

interface TestResults {
  execTime:string,
  execId:string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers:[CommsService]
})

export class AppComponent implements OnInit {
  title = 'app';
  servers:ServerInfo[] = [];
  workingServer:ServerInfo;
  workingServerLabel:string;
  selectedReportsForm:FormGroup;
  busyDialog:MatDialogRef<BusyDialog>;
  progressDialog:MatDialogRef<ProgressDialog>;
 
  constructor(private dataService: DataService, private commsService: CommsService, private dialog:MatDialog) {
  
  }

  ngOnInit(){ //test intitialization of some server entries.

    this.selectedReportsForm = new FormGroup({
      iterationsControl : new FormControl('5',[<any>Validators.min(1),<any>Validators.required]),
      thinkTimeControl : new FormControl('10',[<any>Validators.min(1),<any>Validators.required])
    });
  }

  addServerMeta(){
    //console.log(serverInfo);
    let srs:ReportInfo[] = [];
    let diagModule:DiagnosticsInfo = {
      reports:srs,
      expanded:true
    };

    let ss:ServerInfo = {
    id:String(uuid()),
    label:this.workingServerLabel,
    domain:'',
    port: 8080,
    path: 'jasperserver-pro',
    username:'',
    password:'',
    notes:'',
    diagnosticsModule:diagModule,
    expanded:true };

    //this.servers.unshift(ss);
    this.workingServerLabel = '';
    this.workingServer = ss;  //TODO: find another means of transmitting the server ref.

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

  deleteReportInfo(server, report){
    for (let r=0; r<server.diagnosticsModule.reports.length;r++){
      if (server.diagnosticsModule.reports[r]==report){
        server.diagnosticsModule.reports.splice(r,1);
      }
    }
  }

  addServerDetail(server){
    //update details and try to update the list of reports.

    //if form is valid, submit this information about the server to be persisted.
    //also refresh the contents of the report list for the server.

    this.workingServer = server;  //TODO: find another means of transmitting the server ref.
   
    this.busyDialog = this.dialog.open(BusyDialog, {
      disableClose: true,
      width: '400px',
      data: { }
    });
    let newFetchPacket:FetchReportListPacket = {
      serverInfo: server,
      dialogR:  this.busyDialog
    };

    this.commsService.updateReportTable(newFetchPacket); //get info from the server
  }

  addSelectedReport(data:any){ 
    
    let nr:ReportInfo = { 
      selected: true,
      iterations: 5,
      thinkTime: 10,
      name: data.row.label, 
      url: data.row.uri
    };

    this.workingServer.diagnosticsModule.reports.unshift(nr);
  } 

  executeReportTests(){
    //jsw.test.start.
    this.progressDialog = this.dialog.open(ProgressDialog, {
      disableClose: true,
      width: '500px',
      data: { }
    });
    //jsw.test.end.
    this.executeReportTestsCall();

  }

  executeReportTestsCall(){
    this.dataService.execLoadTest(this.workingServer).subscribe((sResp) => {
      var serverResponse = sResp;
      alert(String(serverResponse.status)); 
    })
  }
}
  
  @Component({
    selector: 'dialog-busy',
    templateUrl: './dialogs/dialog.busy.window.html',
  })
  export class BusyDialog { 
  
    constructor(
      public dialogRef: MatDialogRef<BusyDialog>,
      @Inject(MAT_DIALOG_DATA) public data: any) { }

  }
  
  @Component({
    selector: 'dialog-progress',
    templateUrl: './dialogs/dialog.progress.window.html',
  })
  export class ProgressDialog { 
  
    constructor(
      public dialogRef: MatDialogRef<ProgressDialog>,
      @Inject(MAT_DIALOG_DATA) public data: any) { }

  }