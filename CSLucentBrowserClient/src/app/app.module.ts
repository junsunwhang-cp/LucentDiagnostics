import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent, SafeHtmlPipe } from './app.component';
import { Ng2TableModule } from 'ng2-table/ng2-table';

import { TreeviewComponent } from './components/treeview/treeview.component';

import { DataService } from './services/data.service';
import { PaginationModule, AlertModule } from 'ngx-bootstrap';
import { CollapsibleModule } from 'angular2-collapsible';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


//import { MatDialogModule, MatProgressBarModule, MatProgressSpinnerModule } from '@angular/material';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';

import { BusyDialog, ProgressDialog } from './app.component';
import { ReportviewComponent } from './components/reportview/reportview.component';

@NgModule({
  declarations: [
    AppComponent,
    TreeviewComponent, 
    BusyDialog,
    ProgressDialog,
    ReportviewComponent,
    SafeHtmlPipe
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    HttpModule, 
    Ng2TableModule,
    PaginationModule.forRoot(),
    BrowserAnimationsModule,
    CollapsibleModule,
    FormsModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatStepperModule
  ],
  entryComponents: [
    BusyDialog, ProgressDialog
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
}) 
export class AppModule { } 
