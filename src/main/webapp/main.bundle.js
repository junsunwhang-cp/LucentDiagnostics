webpackJsonp(["main"],{

/***/ "../../../../../src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".ng-valid[required], .ng-valid.required  {\r\n    border-left: 5px solid #42A948; /* green */\r\n}\r\n  \r\n.ng-invalid:not(form)  {\r\n    border-left: 5px solid #a94442; /* red */\r\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div style=\"margin-left:20px;text-align:left;font-family:arial;color:#337ab7\">\r\n    <h3><b>Lucent Utilities - a.04</b></h3>\r\n</div>\r\n\r\n<!-- TODO: remove manual persistence after normal version is implemented. -->\r\n\r\n<!--\r\n<div style='margin-left:20px;'>\r\n    <button (click)=\"onUpdateMeta()\">Persist meta data (dev purpose only)</button>\r\n    <button (click)=\"testDataFetch()\">TestDataFetch</button>\r\n</div>\r\n<br>\r\n-->\r\n<div style='margin-left:20px;margin-right:20px;'>\r\n    <!-- <form (submit)=\"addServerMeta(serverInfo.value)\"> -->\r\n        <form (submit)=\"addServerMeta()\">\r\n        <div>\r\n            <!--\r\n            <table>\r\n                <td style=\"width:10%;text-align:right;\"><label for=\"serverInfo\">Add Server Instance:</label></td>\r\n                <td style=\"width:60%;\"><input type=\"text\" #serverInfo></td>\r\n                <td style=\"width:30%;\"></td>\r\n            </table>\r\n        -->\r\n        <table style=\"width:50%;\">\r\n                <!--    \r\n                <tr><label for=\"serverInfo\">Add Server Instance:</label></tr>\r\n                <tr><input type=\"text\" #serverInfo></tr> -->\r\n                Add Server Definition:\r\n                <tr><input [(ngModel)]='workingServerLabel' name='workingServerLabel' type=\"text\"></tr>\r\n            </table>\r\n        </div>\r\n    </form>\r\n\r\n</div>\r\n\r\n<div style=\"margin-left:20px; margin-right:20px; margin-top:20px; margin-bottom:20px;\">\r\n    <collapsible-list [type]=\"collapsibleType\">\r\n        <collapsible-list-item *ngFor=\"let server of servers; let i = index\">\r\n\r\n            <collapsible-header class=\"waves-effect\" style=\"background-color:darkgrey\">\r\n                <i class=\"fa fa-chevron-down\"\r\n                [ngClass]=\"{\r\n                  'fa-chevron-down': server.expanded,\r\n                  'fa-chevron-right': !server.expanded\r\n                }\"\r\n                aria-hidden=\"true\"></i>\r\n                <b>{{server.label}} <span style=\"color:lightslategrey\"> - http://{{server.domain}}:{{server.port}}/{{server.path}} </span></b> \r\n            </collapsible-header>\r\n\r\n            <collapsible-body  [expanded]=\"server.expanded\"\r\n            (toggleState)=\"server.expanded = $event\">\r\n            <p style=\"text-align: left\">\r\n                {{server.url}}</p>\r\n                <!-- add detail inputs and option to delete server entry -->\r\n\r\n                <!-- <div class=\"container\"> -->\r\n                    <form #serverForm=\"ngForm\" (ngSubmit)=\"addServerDetail(server)\">\r\n                        <table>\r\n                            <tr>\r\n                            <td style=\"width:35%;\">\r\n                                <!-- <label for=\"domain\">Domain:</label> -->\r\n                                <div class=\"form-group\" style=\"padding-right:40px;\">\r\n                                Host Name <label for=\"required\"> ( Required ) </label>\r\n                                <!-- <input style=\"padding:3px; height:15px;\" formControlName='serverDomainControl' required name='serverDomain' type=\"text\" class='form-control'> -->\r\n                                <input style=\"padding:3px; height:15px;\" [(ngModel)]='server.domain' required name='serverDomain' #serverDomain=\"ngModel\" type=\"text\" class='form-control'>\r\n                                <div *ngIf=\"serverDomain.invalid && (serverDomain.dirty || serverDomain.touched)\" class=\"alert alert-danger\">\r\n                                    Host name is required.\r\n                                </div>\r\n                                </div>\r\n                                <!-- <label for=\"port\">Port:</label>-->\r\n                                <div class=\"form-group\" style=\"padding-right:40px;\">\r\n                                Port <label for=\"required\"> ( Required ) </label>\r\n                                \r\n                                <!-- <input  style=\"padding:3px; height:15px;\" formControlName='serverPortControl' required name='serverPort' type=\"number\" class='form-control'> -->\r\n                                <input  style=\"padding:3px; height:15px;\" [(ngModel)]='server.port' required name='serverPort' #serverPort=\"ngModel\" type=\"number\" class='form-control'>\r\n                                <div *ngIf=\"serverPort.invalid && (serverPort.dirty || serverPort.touched)\" class=\"alert alert-danger\">\r\n                                        Port number is required.\r\n                                    </div>\r\n                                </div>\r\n                                <!--<label for=\"path\">Path:</label>-->\r\n                                <div class=\"form-group\" style=\"padding-right:40px;\">\r\n                                App Name <label for=\"required\"> ( Required ) </label>\r\n                                \r\n                                <!-- <input  style=\"padding:3px; height:15px;\" formControlName='serverPathControl' required name='serverPath' type=\"text\" class='form-control'> -->\r\n                                <input  style=\"padding:3px; height:15px;\" [(ngModel)]='server.path' required name='serverPath' #serverPath=\"ngModel\" type=\"text\" class='form-control'>\r\n                                <div *ngIf=\"serverPath.invalid && (serverPath.dirty || serverPath.touched)\" class=\"alert alert-danger\">\r\n                                        Web application name is required.\r\n                                    </div>\r\n                                </div>\r\n                            </td>\r\n                            <td style=\"width:35%;\">\r\n\r\n                                <div class=\"form-group\" style=\"padding-right:40px;\">\r\n                                Username <label for=\"required\"> ( Required ) </label>\r\n                                \r\n                                <!-- <input style=\"padding:3px; height:15px;\" formControlName='serverUsernameControl' required name='serverUsername' type=\"text\" class='form-control'> -->\r\n                                <input style=\"padding:3px; height:15px;\" [(ngModel)]='server.username' required name='serverUsername' #serverUsername=\"ngModel\" type=\"text\" class='form-control'>\r\n                                    <div *ngIf=\"serverUsername.invalid && (serverUsername.dirty || serverUsername.touched)\" class=\"alert alert-danger\">\r\n                                        User name is required.\r\n                                    </div>\r\n                                </div>\r\n\r\n                                <div class=\"form-group\" style=\"padding-right:40px;\">\r\n                                Password <label for=\"required\"> ( Required ) </label>\r\n                                \r\n                                <!-- <input  style=\"padding:3px; height:15px;\" formControlName='serverPasswordControl' required name='serverPassword' type=\"text\" class='form-control'> -->\r\n                                <input  style=\"padding:3px; height:15px;\" [(ngModel)]='server.password' required name='serverPassword' #serverPassword=\"ngModel\" type=\"text\" class='form-control'>\r\n                                <div *ngIf=\"serverPassword.invalid && (serverPassword.dirty || serverPassword.touched)\" class=\"alert alert-danger\">\r\n                                        Password is required.\r\n                                    </div>\r\n                                </div>\r\n                            </td>\r\n                            <td style=\"width:30%;\">\r\n                                <!--<label for=\"path\">Notes:</label>-->\r\n                                Notes<textarea [(ngModel)]='server.notes' name='serverNotes' #serverNotes=\"ngModel\" style=\"height:150px;\" >\r\n                                    {{server.notes}}</textarea>\r\n                            </td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td>\r\n                                    <div style=\"text-align:left;\">\r\n                                            <button type=\"submit\" class=\"btn btn-primary\" style=\"height:30px;\"\r\n                                            [disabled]=\"serverForm.invalid\"\r\n                                            >refresh</button>\r\n                                    </div> \r\n                                </td>\r\n                                <td>\r\n                                </td>\r\n                                <td>\r\n                                    <div style=\"text-align:right;\">\r\n                                        <button  (click)=\"deleteServerInfo(server)\">Delete server definition</button>\r\n                                    </div>\r\n                                </td>\r\n                            </tr>\r\n                            <tr>\r\n                                <br>\r\n                            </tr>\r\n                        </table>\r\n                    </form>\r\n                <!-- </div> -->\r\n                \r\n                <div style=\"margin-left:5px; margin-right:5px; margin-top:5px; margin-bottom:5px;\">\r\n                    <collapsible-list [type]=\"collapsibleType\">\r\n                        <collapsible-list-item>\r\n                        \r\n                        <collapsible-header class=\"waves-effect\" style=\"background-color:lightgrey\">\r\n                            <i class=\"fa fa-chevron-down\"\r\n                            [ngClass]=\"{\r\n                              'fa-chevron-down': server.diagnosticsModule.expanded,\r\n                              'fa-chevron-right': !server.diagnosticsModule.expanded\r\n                            }\"\r\n                            aria-hidden=\"true\"></i>\r\n                            <b>Load Test<span style=\"color:lightslategrey\"></span></b> \r\n                        </collapsible-header>\r\n            \r\n                        <collapsible-body  [expanded]=\"server.diagnosticsModule.expanded\"\r\n                        (toggleState)=\"server.diagnosticsModule.expanded = $event\">\r\n\r\n\r\n                            <!-- jsw.test.start -->\r\n                            <!--\r\n                            <div>\r\n                                <dialog-overview-example>loading</dialog-overview-example>\r\n                            </div>\r\n                            -->\r\n                            <!-- jsw.test.end -->\r\n                            \r\n                            <app-treeview [serverid]='server.id'></app-treeview>\r\n\r\n                            <h4><b>Selected Reports:</b></h4>\r\n                            <!-- <form #reportExecsForm=\"ngForm\" [formGroup]=\"selectedReportsForm\" (ngSubmit)=\"executeReportTests()\"> -->\r\n                            <form #reportExecsForm=\"ngForm\" (ngSubmit)=\"executeReportTests(server.id)\"> \r\n                                <ul style=\"list-style-type:disc\">         \r\n<!--\r\n                                    <input type='hidden' name='serverid' id='serverid' value='{{server.id}}'>\r\n-->\r\n                                    <li *ngFor=\"let report of server.diagnosticsModule.reports; let i = index\"> <b>{{report.name}}</b> ( {{report.url}} ) <button  (click)=\"deleteReportInfo(server,report)\"> X </button><br> \r\n                                        <table>\r\n                                            <tr>\r\n                                                <td style=\"width:50%;\">\r\n                                                    <div  style=\"padding-right:40px;\">\r\n\r\n                                                                \r\n                                                        Number of report iterations <label for=\"required\"> ( Required ) </label>\r\n                                                        <input  style=\"padding:3px; height:15px;\" [(ngModel)]='report.iterations' required name='iterations'  #iterations=\"ngModel\" \r\n                                                        type=\"number\" class='form-control'>\r\n                                                        <div *ngIf=\"iterations.invalid && (iterations.dirty || iterations.touched)\" class=\"alert alert-danger\">\r\n                                                                Report iterations value is required.\r\n                                                        </div>\r\n\r\n\r\n                                                        <!--\r\n                                                        Number of report execution iterations <label for=\"required\"> ( Required ) </label>\r\n                                                        <input required class=\"form-group\" formControlName=\"iterationsControl\" style=\"padding:3px; height:15px;\" name='reportIterations' \r\n                                                        type=\"number\" class='form-control'>  \r\n                                                        -->\r\n                                                        <!--    \r\n                                                        <input [formControl]=\"minRangeFormControl\" style=\"padding:3px; height:15px;\" [(ngModel)]='report.iterations' required name='reportIterations' \r\n                                                        #reportIterations=\"ngModel\" type=\"number\"> \r\n                                                        -->\r\n                                                        <!--\r\n                                                        <div *ngIf=\"reportIterations{{i}}.invalid && (reportIterations{{i}}.dirty || reportIterations{{i}}.touched)\" class=\"alert alert-danger\">\r\n                                                                Number of iterations for the report to be run is required.\r\n                                                            </div>\r\n                                                        -->\r\n                                                    </div>\r\n                                                </td>\r\n                                                <td style=\"width:50%;\">\r\n                                                        <div style=\"padding-right:40px;\">\r\n                                                        Think time between executions in seconds <label for=\"required\"> ( Required ) </label>\r\n                                                    \r\n                                                        <input  style=\"padding:3px; height:15px;\" [(ngModel)]='report.thinkTime' required name='thinkTime' #thinkTime=\"ngModel\" type=\"number\" class='form-control'>\r\n                                                        <div *ngIf=\"thinkTime.invalid && (thinkTime.dirty || thinkTime.touched)\" class=\"alert alert-danger\">\r\n                                                                Report think time value is required.\r\n                                                        </div>\r\n\r\n                                                    <!--\r\n                                                    <input required class=\"form-group\" formControlName='thinkTimeControl' style=\"padding:3px; height:15px;\" name='reportThinktime' \r\n                                                    type=\"number\" class='form-control'>                                        \r\n                                                    -->\r\n                                                    <!--\r\n                                                    <input min=\"1\" style=\"padding:3px; height:15px;\" [(ngModel)]='report.thinkTime' required name='reportThinktime' \r\n                                                    #reportThinktime=\"ngModel\" type=\"number\">\r\n                                                    -->\r\n                                                    <!--\r\n                                                    <div *ngIf=\"reportThinktime{{i}}.invalid && (reportThinktime{{i}}.dirty || reportThinktime{{i}}.touched)\" class=\"alert alert-danger\">\r\n                                                            Number of seconds between report iterations is required.\r\n                                                        </div>\r\n                                                    -->\r\n                                                    </div>\r\n                                                </td>\r\n                                            </tr>\r\n                                        </table>\r\n                                    </li>\r\n                                </ul>\r\n                                <div style=\"text-align:left;\">\r\n                                        <button type=\"submit\" class=\"btn btn-primary\" style=\"height:30px;\"\r\n                                        [disabled]=\"(reportExecsForm.invalid || server.diagnosticsModule.reports.length<1)\"\r\n                                        >execute tests</button>\r\n                                </div> \r\n                            </form>             \r\n                            <hr>\r\n                            <!-- <h4><b>Test Results:</b></h4> -->\r\n                            <!-- <div innerHtml=\"{{testResultsHtml}}\"></div> -->\r\n                            \r\n                            <!--\r\n                            <div [innerHtml]=\"testResultsHtml | SafeHtml\"></div>\r\n                            -->\r\n                            <div style=\"position:relative;\" id='reportBottom_{{server.id}}'></div>\r\n                            <div style=\"position:relative;\" [innerHtml]=\"getServerRuntimeHtml(server.id) | SafeHtml\"></div>\r\n\r\n                            \r\n                           <!--\r\n                            <p></p>\r\n                            <app-reportview></app-reportview>\r\n                            <p></p>\r\n                           -->\r\n                        </collapsible-body>\r\n\r\n                    </collapsible-list-item>\r\n                </collapsible-list>\r\n                </div>\r\n            </collapsible-body>\r\n\r\n        </collapsible-list-item>\r\n    </collapsible-list>\r\n\r\n</div>\r\n\r\n \r\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "d", function() { return SafeHtmlPipe; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return BusyDialog; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return ProgressDialog; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_data_service__ = __webpack_require__("../../../../../src/app/services/data.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__comms_service__ = __webpack_require__("../../../../../src/app/comms.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_uuid__ = __webpack_require__("../../../../uuid/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_uuid___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_uuid__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};





//jsw.test.start.

var SafeHtmlPipe = (function () {
    function SafeHtmlPipe(sanitized) {
        this.sanitized = sanitized;
    }
    SafeHtmlPipe.prototype.transform = function (value) {
        //console.log(this.sanitized.bypassSecurityTrustHtml(value))
        return this.sanitized.bypassSecurityTrustHtml(value);
    };
    SafeHtmlPipe = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Pipe"])({ name: 'SafeHtml' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_4__angular_platform_browser__["DomSanitizer"]])
    ], SafeHtmlPipe);
    return SafeHtmlPipe;
}());

var AppComponent = (function () {
    function AppComponent(dataService, commsService, dialog) {
        this.dataService = dataService;
        this.commsService = commsService;
        this.dialog = dialog;
        this.title = 'app';
        this.servers = [];
        this.serverRuntimes = [];
        this.testsConcluded = false; //used for modal global wait state.
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        /*
            this.selectedReportsForm = new FormGroup({
              iterationsControl : new FormControl('3',[<any>Validators.min(1),<any>Validators.required]),
              thinkTimeControl : new FormControl('5',[<any>Validators.min(1),<any>Validators.required])
            });
            */
        //populate with server meta data if any exists already.
        this.dataService.getServerMeta().subscribe(function (serverMeta) {
            _this.servers = serverMeta;
            //console.log(String(this.servers));
            //iterate through server entries and ensure report list is populated with a non null array.
            var serverEntryLength = _this.servers.length;
            for (var i = 0; i < serverEntryLength; i++) {
                console.log(_this.servers[i]);
                _this.servers[i].diagnosticsModule.reports = new Array();
                var sri = {
                    id: String(_this.servers[i].id),
                    innerhtml: ''
                };
                _this.serverRuntimes.push(sri);
            }
        });
    };
    AppComponent.prototype.addServerMeta = function () {
        //console.log(serverInfo);
        var srs = [];
        var diagModule = {
            reports: srs,
            expanded: true
        };
        var serverId = Object(__WEBPACK_IMPORTED_MODULE_3_uuid__["v4"])();
        var ss = {
            id: String(serverId),
            label: this.workingServerLabel,
            domain: '',
            port: 8080,
            path: 'jasperserver-pro',
            username: '',
            password: '',
            notes: '',
            diagnosticsModule: diagModule,
            expanded: true
        };
        var sri = {
            id: String(serverId),
            innerhtml: ''
        };
        //this.servers.unshift(ss);
        this.workingServerLabel = '';
        // this.workingServer = ss;  //TODO: find another means of transmitting the server ref.
        this.servers.unshift(ss);
        this.serverRuntimes.unshift(sri);
        return false;
    };
    AppComponent.prototype.deleteServerInfo = function (server) {
        for (var i = 0; i < this.servers.length; i++) {
            if (this.servers[i] == server) {
                this.servers.splice(i, 1);
            }
        }
    };
    AppComponent.prototype.deleteReportInfo = function (server, report) {
        for (var r = 0; r < server.diagnosticsModule.reports.length; r++) {
            if (server.diagnosticsModule.reports[r] == report) {
                server.diagnosticsModule.reports.splice(r, 1);
            }
        }
    };
    AppComponent.prototype.addServerDetail = function (server) {
        //update details and try to update the list of reports.
        //if form is valid, submit this information about the server to be persisted.
        //also refresh the contents of the report list for the server.
        //this.workingServer = server;  //TODO: find another means of transmitting the server ref.
        this.busyDialog = this.dialog.open(BusyDialog, {
            disableClose: true,
            width: '400px',
            data: {}
        });
        var newFetchPacket = {
            serverInfo: server,
            dialogR: this.busyDialog
        };
        this.commsService.updateReportTable(newFetchPacket); //get info from the server
    };
    AppComponent.prototype.addSelectedReport = function (treeServerId, data) {
        var nr = {
            selected: true,
            iterations: 3,
            thinkTime: 5,
            name: data.row.label,
            url: data.row.uri
        };
        this.getServerInstance(treeServerId).diagnosticsModule.reports.unshift(nr);
        //this.workingServer.diagnosticsModule.reports.unshift(nr);
    };
    AppComponent.prototype.executeReportTests = function (serverid) {
        //console.log('submitted val:' + subVal);
        this.progressDialog = this.dialog.open(ProgressDialog, {
            disableClose: true,
            width: '500px',
            data: {}
        });
        this.executeReportTestsCall(serverid);
    };
    AppComponent.prototype.executeReportTestsCall = function (serverid) {
        var _this = this;
        var relatedServer = this.getServerInstance(serverid);
        this.dataService.execLoadTest(relatedServer).subscribe(function (sResp) {
            _this.testsConcluded = false;
            var serverResponse = sResp;
        });
        this.testIntervalId = setInterval(function () { return _this.checkStatusReportTests(serverid); }, 1000);
    };
    AppComponent.prototype.checkStatusReportTests = function (serverid) {
        var _this = this;
        if (this.testsConcluded === true) {
            clearInterval(this.testIntervalId); //no need to update status.
        }
        else {
            //get results.
            var relatedServer_1 = this.getServerInstance(serverid);
            this.dataService.execStatusCheck(relatedServer_1).subscribe(function (sResp) {
                //update progress bar.
                var status = sResp.status;
                var completed = sResp.totalcompleted;
                var assigned = sResp.totalassigned;
                _this.progressDialog.componentInstance.setProgress((completed / assigned) * 100);
                if ((typeof assigned != 'undefined') && (typeof completed != 'undefined') && (completed === assigned)) {
                    //if tests are complete:
                    //  1.set the flag to indicate tests are done
                    //  2.close the dialog.          
                    //jsw.test.start
                    //window.open("/viewreport?LoadTestId=testId1_jswTest", "_blank");
                    _this.dataService.viewReportResults(relatedServer_1).subscribe(function (gResp) {
                        //var sample = gResp.text();
                        _this.setServerRuntimeHtml(serverid, gResp.text());
                        var tempInterval = setInterval(function () {
                            window.document.getElementById('reportBottom_' + serverid).scrollIntoView(true);
                            clearInterval(tempInterval);
                        }, 1000);
                        _this.testsConcluded = true;
                        _this.progressDialog.close();
                    });
                    //jsw.test.end
                }
            });
        }
    };
    AppComponent.prototype.getServerInstance = function (serverid) {
        var i;
        for (i = 0; i < this.servers.length; i++) {
            if (this.servers[i].id === serverid) {
                return this.servers[i];
            }
        }
    };
    AppComponent.prototype.setServerRuntimeHtml = function (serverid, inner) {
        var i;
        for (i = 0; i < this.serverRuntimes.length; i++) {
            if (this.serverRuntimes[i].id === serverid) {
                this.serverRuntimes[i].innerhtml = inner;
            }
        }
    };
    AppComponent.prototype.getServerRuntimeHtml = function (serverid) {
        var i;
        for (i = 0; i < this.serverRuntimes.length; i++) {
            if (this.serverRuntimes[i].id === serverid) {
                return this.serverRuntimes[i].innerhtml;
            }
        }
        return null;
    };
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            styles: [__webpack_require__("../../../../../src/app/app.component.css")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__comms_service__["a" /* CommsService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_data_service__["a" /* DataService */], __WEBPACK_IMPORTED_MODULE_2__comms_service__["a" /* CommsService */], __WEBPACK_IMPORTED_MODULE_5__angular_material__["i" /* MatDialog */]])
    ], AppComponent);
    return AppComponent;
}());

var BusyDialog = (function () {
    function BusyDialog(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
    }
    BusyDialog = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'dialog-busy',
            template: __webpack_require__("../../../../../src/app/dialogs/dialog.busy.window.html"),
        }),
        __param(1, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Inject"])(__WEBPACK_IMPORTED_MODULE_5__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_5__angular_material__["k" /* MatDialogRef */], Object])
    ], BusyDialog);
    return BusyDialog;
}());

var ProgressDialog = (function () {
    function ProgressDialog(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.progressPerc = 0;
    }
    ProgressDialog.prototype.setProgress = function (progress) {
        this.progressPerc = progress;
    };
    ProgressDialog = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'dialog-progress',
            template: __webpack_require__("../../../../../src/app/dialogs/dialog.progress.window.html"),
        }),
        __param(1, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Inject"])(__WEBPACK_IMPORTED_MODULE_5__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_5__angular_material__["k" /* MatDialogRef */], Object])
    ], ProgressDialog);
    return ProgressDialog;
}());



/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ng2_table_ng2_table__ = __webpack_require__("../../../../ng2-table/ng2-table.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ng2_table_ng2_table___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_ng2_table_ng2_table__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__components_treeview_treeview_component__ = __webpack_require__("../../../../../src/app/components/treeview/treeview.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__services_data_service__ = __webpack_require__("../../../../../src/app/services/data.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_ngx_bootstrap__ = __webpack_require__("../../../../ngx-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_angular2_collapsible__ = __webpack_require__("../../../../angular2-collapsible/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_reportview_reportview_component__ = __webpack_require__("../../../../../src/app/components/reportview/reportview.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};











//import { MatDialogModule, MatProgressBarModule, MatProgressSpinnerModule } from '@angular/material';



var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_6__components_treeview_treeview_component__["a" /* TreeviewComponent */],
                __WEBPACK_IMPORTED_MODULE_4__app_component__["b" /* BusyDialog */],
                __WEBPACK_IMPORTED_MODULE_4__app_component__["c" /* ProgressDialog */],
                __WEBPACK_IMPORTED_MODULE_12__components_reportview_reportview_component__["a" /* ReportviewComponent */],
                __WEBPACK_IMPORTED_MODULE_4__app_component__["d" /* SafeHtmlPipe */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_8_ngx_bootstrap__["a" /* AlertModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["BrowserModule"],
                __WEBPACK_IMPORTED_MODULE_3__angular_http__["c" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_5_ng2_table_ng2_table__["Ng2TableModule"],
                __WEBPACK_IMPORTED_MODULE_8_ngx_bootstrap__["b" /* PaginationModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_10__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_9_angular2_collapsible__["a" /* CollapsibleModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["h" /* ReactiveFormsModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["b" /* MatAutocompleteModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["c" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["d" /* MatButtonToggleModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["e" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["f" /* MatCheckboxModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["g" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["h" /* MatDatepickerModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["j" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["l" /* MatExpansionModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["m" /* MatGridListModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["n" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["o" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["p" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["q" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["r" /* MatNativeDateModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["s" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["t" /* MatProgressBarModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["u" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["v" /* MatRadioModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["w" /* MatRippleModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["x" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["y" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["A" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["z" /* MatSlideToggleModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["B" /* MatSnackBarModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["C" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["E" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["F" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["G" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["H" /* MatTooltipModule */],
                __WEBPACK_IMPORTED_MODULE_11__angular_material__["D" /* MatStepperModule */]
            ],
            entryComponents: [
                __WEBPACK_IMPORTED_MODULE_4__app_component__["b" /* BusyDialog */], __WEBPACK_IMPORTED_MODULE_4__app_component__["c" /* ProgressDialog */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_7__services_data_service__["a" /* DataService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "../../../../../src/app/comms.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CommsService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__ = __webpack_require__("../../../../rxjs/_esm5/Subject.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var CommsService = (function () {
    function CommsService() {
        this.messageSource = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__["b" /* Subject */]();
        this.messageObs$ = this.messageSource.asObservable();
    }
    /*
    testAlert(server:ServerInfo, note:string){
        this.messageSource.next(note);
        alert(note);
    }
    */
    CommsService.prototype.updateReportTable = function (fetchInfo) {
        this.messageSource.next(fetchInfo);
    };
    CommsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])()
    ], CommsService);
    return CommsService;
}());



/***/ }),

/***/ "../../../../../src/app/components/reportview/reportview.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/reportview/reportview.component.html":
/***/ (function(module, exports) {

module.exports = "<h4><b>Test Results:</b></h4>\n<div id='report_view'></div>\n"

/***/ }),

/***/ "../../../../../src/app/components/reportview/reportview.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportviewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ReportviewComponent = (function () {
    function ReportviewComponent() {
    }
    ReportviewComponent.prototype.ngOnInit = function () {
    };
    ReportviewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-reportview',
            template: __webpack_require__("../../../../../src/app/components/reportview/reportview.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/reportview/reportview.component.css")],
            encapsulation: __WEBPACK_IMPORTED_MODULE_0__angular_core__["ViewEncapsulation"].None
        }),
        __metadata("design:paramtypes", [])
    ], ReportviewComponent);
    return ReportviewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/treeview/treeview.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".hoverTable{\r\n    border-collapse:collapse; \r\n}\r\n.hoverTable td{ \r\n    padding:2px;\r\n}\r\n.hoverTableBackground{\r\n    background:lightskyblue;\r\n}\r\n.hoverTable tr:hover {\r\n    background-color: #ffff99;\r\n}\r\n\r\n.table-hover > tbody > tr:hover {\r\n    background-color: blue;\r\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/treeview/treeview.component.html":
/***/ (function(module, exports) {

module.exports = "<!-- <div style=\"margin-left:20px; margin-right:20px; margin-top:20px; margin-bottom:20px;\"> -->\n<h4><b>Repository Reports:</b></h4>\n<div>\n  <ng-table ng-mouseover=\"table-hover\" id=\"reportViewTable_{{serverid}}\" [config]=\"getCurrentInstance(serverid).config\"\n    (tableChanged)=\"onChangeTable(getCurrentInstance(serverid).config)\"\n    (cellClicked)=\"onCellClick($event)\"\n    [rows]=\"getCurrentInstance(serverid).instrows\" [columns]=\"getCurrentInstance(serverid).instcolumns\">\n  </ng-table>\n  <pagination *ngIf=\"getCurrentInstance(serverid).config.paging\" class=\"pagination-sm\"\n    [(ngModel)]=\"getCurrentInstance(serverid).page\"\n    [totalItems]=\"getCurrentInstance(serverid).length\"\n    [itemsPerPage]=\"getCurrentInstance(serverid).itemsPerPage\"\n    [maxSize]=\"getCurrentInstance(serverid).maxSize\"\n    [boundaryLinks]=\"true\"\n    [rotate]=\"false\"\n    (pageChanged)=\"onChangeTable(getCurrentInstance(serverid).config,$event)\"\n    (numPages)=\"getCurrentInstance(serverid).numPages = $event\">\n  </pagination>\n</div>"

/***/ }),

/***/ "../../../../../src/app/components/treeview/treeview.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TreeviewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_data_service__ = __webpack_require__("../../../../../src/app/services/data.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__comms_service__ = __webpack_require__("../../../../../src/app/comms.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var template = __webpack_require__("../../../../../src/app/components/treeview/treeview.component.html");
var TreeviewComponent = (function () {
    function TreeviewComponent(dataService, appComponent, commsService) {
        this.dataService = dataService;
        this.appComponent = appComponent;
        this.commsService = commsService;
        this.serverid = '';
        //test.start.  TODO: remove.
        //private data:Array<any> = TableData; //jsw.localtest.2 of 3
        //private data:Array<any>; //jsw.deployed.1 of 2
        //test.end.
        //private dataMap = new Map<string,any>();
        this.instanceInfoMap = new Map();
    }
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
    TreeviewComponent.prototype.createInstanceInfo = function () {
        var newTreeInstance = {
            instrows: [],
            instcolumns: [
                { title: 'Label', name: 'label', filtering: { filterString: '', placeholder: 'Filter by label' } },
                { title: 'URI', name: 'uri', filtering: { filterString: '', placeholder: 'Filter by uri' } }
            ],
            data: null,
            page: 1,
            itemsPerPage: 10,
            maxSize: 5,
            numPages: 1,
            length: 0,
            config: {
                paging: true,
                sorting: { columns: null },
                filtering: { filterString: '' },
                className: ['table-striped', 'table-bordered', 'table-condensed'],
                selectedRowClass: 'selectedRow'
            }
        };
        newTreeInstance.config.sorting.columns = newTreeInstance.instcolumns;
        this.instanceInfoMap.set(this.serverid, newTreeInstance);
        return newTreeInstance;
    };
    TreeviewComponent.prototype.getCurrentInstance = function (sid) {
        //console.log ('get current instance from=' + sid + ', serverid=' + this.serverid);
        return this.instanceInfoMap.get(sid);
    };
    TreeviewComponent.prototype.ngOnInit = function () {
        var _this = this;
        //jsw.localtest.3 of 3 
        //this.onChangeTable(this.config);
        var treeInst = this.createInstanceInfo();
        this.commsService.messageObs$.subscribe(function (fetchPacket) {
            //alert('message observed');
            //console.log('treeview subscribe function access.');
            //jsw.deployed.2 of 2
            _this.dataService.getRepoReports(fetchPacket.serverInfo.id, fetchPacket.serverInfo.domain, String(fetchPacket.serverInfo.port), fetchPacket.serverInfo.path, fetchPacket.serverInfo.username, fetchPacket.serverInfo.password, fetchPacket.serverInfo.label, fetchPacket.serverInfo.notes).subscribe(function (reportServerData) {
                //console.log(reportServerData); 
                //this.data = reportServerData;
                //this.dataMap.set(this.serverid, reportServerData);
                _this.getCurrentInstance(_this.serverid).data = reportServerData;
                _this.onChangeTable(_this.getCurrentInstance(_this.serverid).config);
                fetchPacket.dialogR.close();
            });
        });
    };
    TreeviewComponent.prototype.ngOnDestroy = function () {
    };
    TreeviewComponent.prototype.externalChangePage = function () {
        this.onChangeTable(this.getCurrentInstance(this.serverid).config);
    };
    /*
      public changePage(page:any, data:Array<any> = this.data):Array<any> {
        let start = (page.page - 1) * page.itemsPerPage;
        let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
        return data.slice(start, end);
      }
    */
    TreeviewComponent.prototype.changePage = function (page, data) {
        if (data === void 0) { data = this.getCurrentInstance(this.serverid).data; }
        var start = (page.page - 1) * page.itemsPerPage;
        var end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
        return data.slice(start, end);
    };
    TreeviewComponent.prototype.changeSort = function (data, config) {
        if (!config.sorting) {
            return data;
        }
        var columns = this.getCurrentInstance(this.serverid).config.sorting.columns || [];
        var columnName = void 0;
        var sort = void 0;
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].sort !== '' && columns[i].sort !== false) {
                columnName = columns[i].name;
                sort = columns[i].sort;
            }
        }
        if (!columnName) {
            return data;
        }
        // simple sorting
        return data.sort(function (previous, current) {
            if (previous[columnName] > current[columnName]) {
                return sort === 'desc' ? -1 : 1;
            }
            else if (previous[columnName] < current[columnName]) {
                return sort === 'asc' ? -1 : 1;
            }
            return 0;
        });
    };
    TreeviewComponent.prototype.changeFilter = function (data, config) {
        var _this = this;
        var filteredData = data;
        this.getCurrentInstance(this.serverid).instcolumns.forEach(function (column) {
            if (column.filtering) {
                filteredData = filteredData.filter(function (item) {
                    return item[column.name].match(column.filtering.filterString);
                });
            }
        });
        if (!config.filtering) {
            return filteredData;
        }
        if (config.filtering.columnName) {
            return filteredData.filter(function (item) {
                return item[config.filtering.columnName].match(_this.getCurrentInstance(_this.serverid).config.filtering.filterString);
            });
        }
        var tempArray = [];
        filteredData.forEach(function (item) {
            var flag = false;
            _this.getCurrentInstance(_this.serverid).instcolumns.forEach(function (column) {
                if (item[column.name].toString().match(_this.getCurrentInstance(_this.serverid).config.filtering.filterString)) {
                    flag = true;
                }
            });
            if (flag) {
                tempArray.push(item);
            }
        });
        filteredData = tempArray;
        return filteredData;
    };
    TreeviewComponent.prototype.onChangeTable = function (config, page) {
        if (page === void 0) { page = { page: this.getCurrentInstance(this.serverid).page,
            itemsPerPage: this.getCurrentInstance(this.serverid).itemsPerPage }; }
        if (config.filtering) {
            Object.assign(this.getCurrentInstance(this.serverid).config.filtering, config.filtering);
        }
        if (config.sorting) {
            Object.assign(this.getCurrentInstance(this.serverid).config.sorting, config.sorting);
        }
        //let filteredData = this.changeFilter(this.data, this.config);
        var filteredData = this.changeFilter(this.getCurrentInstance(this.serverid).data, this.getCurrentInstance(this.serverid).config);
        var sortedData = this.changeSort(filteredData, this.getCurrentInstance(this.serverid).config);
        this.getCurrentInstance(this.serverid).instrows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
        this.getCurrentInstance(this.serverid).length = sortedData.length;
    };
    TreeviewComponent.prototype.onCellClick = function (data) {
        //console.log(data);
        //console.log("on cell click from server: " + this.serverid);
        this.appComponent.addSelectedReport(this.serverid, data);
        /*
        let index = this.data.indexOf(data.row);
        
        document.getElementById('reportViewTable')  on('click','tr', function(event:any)){
          $(this).classList.add("hoverTableBackground");
        }
        */
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
        __metadata("design:type", String)
    ], TreeviewComponent.prototype, "serverid", void 0);
    TreeviewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-treeview',
            template: template,
            styles: [__webpack_require__("../../../../../src/app/components/treeview/treeview.component.css")],
            encapsulation: __WEBPACK_IMPORTED_MODULE_0__angular_core__["ViewEncapsulation"].None
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_data_service__["a" /* DataService */], __WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */], __WEBPACK_IMPORTED_MODULE_3__comms_service__["a" /* CommsService */]])
    ], TreeviewComponent);
    return TreeviewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/dialogs/dialog.busy.window.html":
/***/ (function(module, exports) {

module.exports = "<h1 mat-dialog-title>Loading report list</h1>\r\n<div mat-dialog-content>\r\n  <!-- <p>Loading report list.</p> -->\r\n  <mat-progress-bar mode=\"indeterminate\"></mat-progress-bar>\r\n</div>\r\n "

/***/ }),

/***/ "../../../../../src/app/dialogs/dialog.progress.window.html":
/***/ (function(module, exports) {

module.exports = "<h1 mat-dialog-title>Executing report tests</h1>\r\n<div mat-dialog-content>\r\n  <!-- <p>Executing report tests.</p> -->\r\n  current progress: {{progressPerc | number: '1.0-0'}}%\r\n  <mat-progress-bar mode=\"determinate\" value=\"{{progressPerc}}\"></mat-progress-bar>\r\n</div> \r\n "

/***/ }),

/***/ "../../../../../src/app/services/data.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DataService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_uuid__ = __webpack_require__("../../../../uuid/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_uuid___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_uuid__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var DataService = (function () {
    function DataService(http) {
        this.http = http;
        console.log('ds connect.');
    }
    //jsw: test only. TODO: remove.
    DataService.prototype.getPosts = function () {
        return this.http.get('https://jsonplaceholder.typicode.com/posts')
            .map(function (res) { return res.json(); });
    };
    //jsw.test.end.
    DataService.prototype.getServerMeta = function () {
        var serverMeta = this.http.get('/serverinfo/meta').map(function (sMeta) { return sMeta.json(); });
        return serverMeta;
    };
    DataService.prototype.getRepoReports = function (id, hostName, port, path, username, password, label, notes) {
        var reportResults = this.http.get(encodeURI('/serverinfo/reportList?' + 'id=' + id + '&hostname=' + hostName +
            '&port=' + port + '&path=' + path + '&username=' + username + '&password=' + password +
            '&label=' + label + '&notes=' + notes)).map(function (res) { return res.json(); });
        return reportResults;
    };
    DataService.prototype.execLoadTest = function (server) {
        var headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Headers */]();
        var newTestId = Object(__WEBPACK_IMPORTED_MODULE_2_uuid__["v4"])();
        server.testId = newTestId;
        headers.append('Content-Type', 'application/json');
        headers.append('LoadTestId', newTestId);
        //headers.append('LoadTestId', 'testId1_jswTest');
        return this.http.post('/loadtest/start', server, { headers: headers }).map(function (res) { return res.json(); });
    };
    DataService.prototype.execStatusCheck = function (server) {
        var headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Headers */]();
        headers.append('Content-Type', 'application/json');
        //headers.append('LoadTestId', 'testId1_jswTest');
        headers.append('LoadTestId', server.testId);
        return this.http.post('/loadtest/status', server, { headers: headers }).map(function (res) { return res.json(); });
    };
    DataService.prototype.viewReportResults = function (server) {
        return this.http.get('/viewreport?LoadTestId=' + server.testId);
    };
    DataService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]])
    ], DataService);
    return DataService;
}());



/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["enableProdMode"])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map