import { Inject, Injectable, Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { DecimalPipe, DOCUMENT } from '@angular/common';
import { DataService} from './services/data.service';
import { resource } from 'selenium-webdriver/http';
import { NgForm, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommsService } from './comms.service';
import { ServerInfo, ServerRuntimeInfo, ReportInfo, DiagnosticsInfo, FetchReportListPacket } from './comms.service';
import { v4 as uuid } from 'uuid';
import { Subscription } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import { DomSanitizer } from '@angular/platform-browser'

//jsw.test.start.
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
//jsw.test.end.

interface TestResults {
  execTime:string,
  execId:string
}

@Pipe({ name: 'SafeHtml'})
export class SafeHtmlPipe implements PipeTransform  {
  constructor(private sanitized: DomSanitizer) {}
  transform(value) {
    //console.log(this.sanitized.bypassSecurityTrustHtml(value))
    return this.sanitized.bypassSecurityTrustHtml(value);
  }
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
  serverRuntimes:ServerRuntimeInfo[] = [];

  //workingServer:ServerInfo;
  workingServerLabel:string;
  //selectedReportsForm:FormGroup;
  busyDialog:MatDialogRef<BusyDialog>;
  progressDialog:MatDialogRef<ProgressDialog>;
  testsConcluded = false; //used for modal global wait state.
  testIntervalId;
  //testResultsHtml = ' ';
  DOCUMENT: any;

  constructor(private dataService: DataService, private commsService: CommsService, private dialog:MatDialog) {
  
  }

  ngOnInit(){ //test intitialization of some server entries.
/*
    this.selectedReportsForm = new FormGroup({
      iterationsControl : new FormControl('3',[<any>Validators.min(1),<any>Validators.required]),
      thinkTimeControl : new FormControl('5',[<any>Validators.min(1),<any>Validators.required])
    });
    */
    //populate with server meta data if any exists already.
    this.dataService.getServerMeta().subscribe((serverMeta) => {
    this.servers = serverMeta;

    //console.log(String(this.servers));
    //iterate through server entries and ensure report list is populated with a non null array.
    var serverEntryLength = this.servers.length;
    for (var i=0; i<serverEntryLength; i++){
      console.log(this.servers[i]);
      this.servers[i].diagnosticsModule.reports = new Array();
      let sri:ServerRuntimeInfo = {
        id:String(this.servers[i].id),
        innerhtml:''
      };
      this.serverRuntimes.push(sri);
    }

  });


  }

  addServerMeta(){
    //console.log(serverInfo);
    let srs:ReportInfo[] = [];
    let diagModule:DiagnosticsInfo = {
      reports:srs,
      expanded:true
    };

    let serverId = uuid();

    let ss:ServerInfo = {
    id:String(serverId),
    label:this.workingServerLabel,
    domain:'',
    port: 8080,
    path: 'jasperserver-pro',
    username:'',
    password:'',
    notes:'',
    diagnosticsModule:diagModule,
    expanded:true };

    let sri:ServerRuntimeInfo = {
      id:String(serverId),
      innerhtml:''
    };

    //this.servers.unshift(ss);
    this.workingServerLabel = '';
   // this.workingServer = ss;  //TODO: find another means of transmitting the server ref.

    this.servers.unshift(ss);
    this.serverRuntimes.unshift(sri);
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

    //this.workingServer = server;  //TODO: find another means of transmitting the server ref.
   
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

  addSelectedReport(treeServerId:string, data:any){ 
    
    let nr:ReportInfo = { 
      selected: true,
      iterations: 3,
      thinkTime: 5,
      name: data.row.label, 
      url: data.row.uri
    };

    this.getServerInstance(treeServerId).diagnosticsModule.reports.unshift(nr);

    //this.workingServer.diagnosticsModule.reports.unshift(nr);
  } 

  executeReportTests(serverid:string){ 

    //console.log('submitted val:' + subVal);

    this.progressDialog = this.dialog.open(ProgressDialog, {
      disableClose: true,
      width: '500px',
      data: { }
    });
    
    this.executeReportTestsCall(serverid);
  }

  executeReportTestsCall(serverid:string){

    let relatedServer:ServerInfo = this.getServerInstance(serverid);

    this.dataService.execLoadTest(relatedServer).subscribe((sResp) => {
      this.testsConcluded = false;
      var serverResponse = sResp;
    })
 
    this.testIntervalId = setInterval( ()=>this.checkStatusReportTests(serverid) , 1000);
  }

  checkStatusReportTests(serverid:string){
    if (this.testsConcluded === true){
      clearInterval(this.testIntervalId); //no need to update status.
    } else { //retrieve status and update the progress indicator.
      //get results.
      let relatedServer:ServerInfo = this.getServerInstance(serverid);
      this.dataService.execStatusCheck(relatedServer).subscribe((sResp) =>{
        //update progress bar.
        var status = sResp.status;
        var completed = sResp.totalcompleted;
        var assigned = sResp.totalassigned;
        this.progressDialog.componentInstance.setProgress( (completed/assigned) * 100 );        

        if ((typeof assigned!='undefined') && (typeof completed!='undefined') && (completed === assigned)) {
          //if tests are complete:
          //  1.set the flag to indicate tests are done
          //  2.close the dialog.          
          this.testsConcluded = true;

          //jsw.test.start
          //window.open("/viewreport?LoadTestId=testId1_jswTest", "_blank");
          this.dataService.viewReportResults(relatedServer).subscribe((gResp) => {
            //var sample = gResp.text();
            this.setServerRuntimeHtml(serverid,gResp.text());
            var tempInterval = setInterval(function(){
              window.document.getElementById('reportBottom_' + serverid).scrollIntoView(true); 
              clearInterval(tempInterval);
            }, 1000);
            //this.testsConcluded = true;
            this.progressDialog.close();
          });
          //jsw.test.end
        } 
      })
    }
  }

  getServerInstance(serverid:string){
    let i:number;
    for (i=0; i<this.servers.length ;i++){
      if (this.servers[i].id === serverid){
        return this.servers[i];
      }
    }
  }

  setServerRuntimeHtml(serverid:string, inner:string){
    let i:number;
    for (i=0; i<this.serverRuntimes.length ;i++){
      if (this.serverRuntimes[i].id === serverid){
        this.serverRuntimes[i].innerhtml = inner;
      }
    }
  }

  getServerRuntimeHtml(serverid:string){
    let i:number;
    for (i=0; i<this.serverRuntimes.length ;i++){
      if (this.serverRuntimes[i].id === serverid){
        return this.serverRuntimes[i].innerhtml;
      }
    }
    return null;
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
  
    progressPerc:number = 0;

    constructor(
      public dialogRef: MatDialogRef<ProgressDialog>,
      @Inject(MAT_DIALOG_DATA) public data: any) { }

    setProgress(progress:number){
      this.progressPerc = progress;
    }

  }



