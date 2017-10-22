import {Component, AfterViewInit, ChangeDetectorRef} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {MdDialogRef, MdIconRegistry} from '@angular/material';
import { TdMediaService } from '@covalent/core';
import {MdDialog} from '@angular/material';
import {MessageComponent} from "../message/message.component";
import {LoginComponent} from "../login/login.component";

@Component({
    selector: 's-login-pg',
    templateUrl: './testpage.component.html',
    styleUrls: ['./testpage.component.scss']
})
export class TestpageComponent implements AfterViewInit {

    sidenavopened = true;
    weekday = new Date().toLocaleString('en-US', {weekday: 'long'});
    year = new Date().toLocaleString('en-US', {year: 'numeric'});
    month = new Date().toLocaleString('en-US', {month: 'long'});
    day = new Date().toLocaleString('en-US', {day: 'numeric'});

    routes: Object[] = [
        {
            title: 'Inbox',
            route: '/',
            icon: 'email',
        }, {
            title: 'Drafts',
            route: '/',
            icon: 'drafts',
        }, {
            title: 'Sent',
            route: '/',
            icon: 'send',
        }, {
            title: 'Trash',
            route: '/',
            icon: 'delete',
        },
    ];


    constructor(public media: TdMediaService,
                private _iconRegistry: MdIconRegistry,
                private _domSanitizer: DomSanitizer,
                private _changeDetectorRef: ChangeDetectorRef,
                public dialog: MdDialog) {

    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this.media.broadcast();
            this._changeDetectorRef.detectChanges();
        });
    }

    toggleSideNav(): void {
        this.sidenavopened = !this.sidenavopened;
    }

    composeMail(): void {
        const dialogRef = this.dialog.open(MessageComponent, {
            height: '70%',
            width: '70%'
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`);
        });
    }

}