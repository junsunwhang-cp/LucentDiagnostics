import { Injectable } from '@angular/core';
import { Subject }    from 'rxjs/Subject';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BusyDialog } from './app.component';

  export interface ServerInfo {
    id ?:string,
    label ?:string,
    domain ?:string,
    port ?: number,
    path ?: string,
    username ?:string,
    password ?:string,
    notes ?:string,
    diagnosticsModule ?:DiagnosticsInfo,
    expanded ?:boolean,
    testId ?:string
  }

  export interface ServerRuntimeInfo {
    id ?:string,
    innerhtml ?:string
  }

  export interface DiagnosticsInfo {
    reports ?:ReportInfo[],
    expanded ?:boolean,
    testResultsHtml ?:string
  }

  export interface ReportInfo { 
    selected ?:boolean,
    iterations ?:number,
    thinkTime ?:number,
    name ?:string,
    url ?:string,
    reportParameters ?:string
  }

  export interface FetchReportListPacket {
    serverInfo: ServerInfo,
    dialogR: MatDialogRef<BusyDialog>
  }

@Injectable()
export class CommsService {

    private messageSource= new Subject<FetchReportListPacket>();
    messageObs$ = this.messageSource.asObservable();

    /*
    testAlert(server:ServerInfo, note:string){
        this.messageSource.next(note);
        alert(note);
    }
    */

    updateReportTable(fetchInfo:FetchReportListPacket){
      this.messageSource.next(fetchInfo);
    }

}


