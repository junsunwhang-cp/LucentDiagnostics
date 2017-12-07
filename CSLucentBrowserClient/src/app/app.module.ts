import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { TreeviewComponent } from './components/treeview/treeview.component';
import { Ng2TableModule } from 'ng2-table/ng2-table';

import { DataService } from './services/data.service';
import { PaginationModule, AlertModule } from 'ngx-bootstrap';
import { CollapsibleModule } from 'angular2-collapsible';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    TreeviewComponent
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpModule,
    Ng2TableModule,
    PaginationModule.forRoot(),
    BrowserAnimationsModule,
    CollapsibleModule
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
