import { Component, OnInit, ViewEncapsulation, OnDestroy, Input } from '@angular/core';
import { DataService} from '../../services/data.service';
import { AppComponent} from '../../app.component';
import { Ng2TableModule } from 'ng2-table/ng2-table';
import { NgTableComponent, NgTableFilteringDirective, NgTablePagingDirective, NgTableSortingDirective } from 'ng2-table/ng2-table';
import { PaginationModule, TabsModule } from 'ngx-bootstrap';
import { NG_TABLE_DIRECTIVES } from 'ng2-table/ng2-table';

import { CommsService } from '../../comms.service';
import { Subscription } from 'rxjs/Subscription';
//import { TableData } from './table-data'; //jsw.localtest.1 of 3
import { ServerInfo, ReportInfo } from '../../comms.service';

let template = require('./treeview.component.html');

interface TreeInstanceInfo {
  instrows ?:Array<any>,
  instcolumns ?:Array<any>,
  data ?: any,
  page:number,
  itemsPerPage:number,
  maxSize:number,
  numPages:number,
  length:number,
  config:any
}

@Component({
  selector: 'app-treeview',
  template,
  styleUrls: ['./treeview.component.css'],  
  encapsulation: ViewEncapsulation.None
})
export class TreeviewComponent implements OnInit { 

  @Input() serverid:string = '';

  //test.start.  TODO: remove.
  //private data:Array<any> = TableData; //jsw.localtest.2 of 3
  //private data:Array<any>; //jsw.deployed.1 of 2
  //test.end.

  //private dataMap = new Map<string,any>();
  private instanceInfoMap = new Map<string,TreeInstanceInfo>();

  //public rows:Array<any> = [];
  //public columns:Array<any> = [
  //  {title: 'Label', name: 'label', filtering: {filterString: '', placeholder: 'Filter by label'}},
  //  {title: 'URI', name: 'uri', filtering: {filterString: '', placeholder: 'Filter by uri'}} 
    /*,
    {title: 'Office', className: ['office-header', 'text-success'], name: 'office', sort: 'asc'},
    {title: 'Extn.', name: 'ext', sort: '', filtering: {filterString: '', placeholder: 'Filter by extn.'}},
    {title: 'Start date', className: 'text-warning', name: 'startDate'},
    {title: 'Salary ($)', name: 'salary'} */
    //];
  /*
  public selRows:Array<any> = [];
  public selColumns:Array<any> = [
    {title: 'Name', name: 'name', filtering: {filterString: '', placeholder: 'Filter by name'}},
    {
      title: 'Position',
      name: 'position',
      sort: false,
      filtering: {filterString: '', placeholder: 'Filter by position'}
    },
    {title: 'Office', className: ['office-header', 'text-success'], name: 'office', sort: 'asc'},
    {title: 'Extn.', name: 'ext', sort: '', filtering: {filterString: '', placeholder: 'Filter by extn.'}},
    {title: 'Start date', className: 'text-warning', name: 'startDate'},
    {title: 'Salary ($)', name: 'salary'}
  ];
*/

/*
  public page:number = 1;
  public itemsPerPage:number = 10;
  public maxSize:number = 5;
  public numPages:number = 1;
  public length:number = 0;
*/  

  /*
  public selPage:number=1;
  public selItemsPerPage:number = 5;
  public selLength:number = 0;
  */

  /*
  public config:any = {
    paging: true,
    sorting: {columns: this.columns},
    filtering: {filterString: ''},
    className: ['table-striped', 'table-bordered', 'table-condensed'],
    selectedRowClass: 'selectedRow'
  };
  */

  private createInstanceInfo():TreeInstanceInfo {

    let newTreeInstance:TreeInstanceInfo = {
      instrows:[],
      instcolumns: [
         {title: 'Label', name: 'label', filtering: {filterString: '', placeholder: 'Filter by label'}},
          {title: 'URI', name: 'uri', filtering: {filterString: '', placeholder: 'Filter by uri'}} ],
      data: null,
      page: 1,
      itemsPerPage: 10,
      maxSize: 5,
      numPages: 1,
      length: 0,
      config: {
        paging: true,
        sorting: {columns: null},
        filtering: {filterString: ''},
        className: ['table-striped', 'table-bordered', 'table-condensed'],
        selectedRowClass: 'selectedRow'
      }
    }
    newTreeInstance.config.sorting.columns = newTreeInstance.instcolumns;
    this.instanceInfoMap.set(this.serverid, newTreeInstance);

    return newTreeInstance; 
  }

  private getCurrentInstance(sid:string):TreeInstanceInfo {
    //console.log ('get current instance from=' + sid + ', serverid=' + this.serverid);
    return this.instanceInfoMap.get(sid);
  }

  constructor(private dataService:DataService, private appComponent:AppComponent, private commsService:CommsService) {
  }
  
  ngOnInit() { 
    //jsw.localtest.3 of 3 
    //this.onChangeTable(this.config);
    let treeInst = this.createInstanceInfo();

    this.commsService.messageObs$.subscribe(
      fetchPacket => {
        //alert('message observed');
        //console.log('treeview subscribe function access.');
        //jsw.deployed.2 of 2
        this.dataService.getRepoReports(fetchPacket.serverInfo.id, fetchPacket.serverInfo.domain, 
          String(fetchPacket.serverInfo.port), fetchPacket.serverInfo.path, 
          fetchPacket.serverInfo.username, fetchPacket.serverInfo.password,
          fetchPacket.serverInfo.label, fetchPacket.serverInfo.notes
        ).subscribe((reportServerData) => {
          //console.log(reportServerData); 
          //this.data = reportServerData;
          //this.dataMap.set(this.serverid, reportServerData);

          this.getCurrentInstance(this.serverid).data = reportServerData;
          this.onChangeTable(this.getCurrentInstance(this.serverid).config);
          fetchPacket.dialogR.close();
        });
      }
    );
  }

  ngOnDestroy(){
  }

  public externalChangePage(){
    this.onChangeTable(this.getCurrentInstance(this.serverid).config);
  }
/*
  public changePage(page:any, data:Array<any> = this.data):Array<any> {
    let start = (page.page - 1) * page.itemsPerPage;
    let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
    return data.slice(start, end);
  }
*/

public changePage(page:any, data:Array<any> = this.getCurrentInstance(this.serverid).data):Array<any> {
  let start = (page.page - 1) * page.itemsPerPage;
  let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
  return data.slice(start, end);
}

  public changeSort(data:any, config:any):any {
    if (!config.sorting) {
      return data;
    }

    let columns = this.getCurrentInstance(this.serverid).config.sorting.columns || [];
    let columnName:string = void 0;
    let sort:string = void 0;

    for (let i = 0; i < columns.length; i++) {
      if (columns[i].sort !== '' && columns[i].sort !== false) {
        columnName = columns[i].name;
        sort = columns[i].sort;
      }
    }

    if (!columnName) {
      return data;
    }

    // simple sorting
    return data.sort((previous:any, current:any) => {
      if (previous[columnName] > current[columnName]) {
        return sort === 'desc' ? -1 : 1;
      } else if (previous[columnName] < current[columnName]) {
        return sort === 'asc' ? -1 : 1;
      }
      return 0;
    });
  }

  public changeFilter(data:any, config:any):any {
    let filteredData:Array<any> = data;
    this.getCurrentInstance(this.serverid).instcolumns.forEach((column:any) => {
      if (column.filtering) {
        filteredData = filteredData.filter((item:any) => {
          return item[column.name].match(column.filtering.filterString);
        });
      }
    });

    if (!config.filtering) {
      return filteredData;
    }

    if (config.filtering.columnName) {
      return filteredData.filter((item:any) =>
        item[config.filtering.columnName].match(this.getCurrentInstance(this.serverid).config.filtering.filterString));
    }

    let tempArray:Array<any> = [];
    filteredData.forEach((item:any) => {
      let flag = false;
      this.getCurrentInstance(this.serverid).instcolumns.forEach((column:any) => {
        if (item[column.name].toString().match(this.getCurrentInstance(this.serverid).config.filtering.filterString)) {
          flag = true;
        }
      });
      if (flag) {
        tempArray.push(item);
      }
    });
    filteredData = tempArray;

    return filteredData;
  }

  public onChangeTable(config:any, page:any = {page: this.getCurrentInstance(this.serverid).page, 
  itemsPerPage: this.getCurrentInstance(this.serverid).itemsPerPage}):any {
    if (config.filtering) {
      Object.assign(this.getCurrentInstance(this.serverid).config.filtering, config.filtering);
    }

    if (config.sorting) {
      Object.assign(this.getCurrentInstance(this.serverid).config.sorting, config.sorting);
    }

    //let filteredData = this.changeFilter(this.data, this.config);
    let filteredData = this.changeFilter(this.getCurrentInstance(this.serverid).data, this.getCurrentInstance(this.serverid).config);

    let sortedData = this.changeSort(filteredData, this.getCurrentInstance(this.serverid).config);
    this.getCurrentInstance(this.serverid).instrows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
    this.getCurrentInstance(this.serverid).length = sortedData.length;
  }

  public onCellClick(data: any): any {
    //console.log(data);
    //console.log("on cell click from server: " + this.serverid);
    this.appComponent.addSelectedReport(this.serverid, data);

    /*
    let index = this.data.indexOf(data.row);
    
    document.getElementById('reportViewTable')  on('click','tr', function(event:any)){
      $(this).classList.add("hoverTableBackground");
    }
    */
    
  }
 
}
