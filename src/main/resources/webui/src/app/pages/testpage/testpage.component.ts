import {Component, AfterViewInit, ChangeDetectorRef} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MdIconRegistry } from '@angular/material';
import { TdMediaService } from '@covalent/core';

@Component({
    selector: 's-login-pg',
    templateUrl: './testpage.component.html',
    styleUrls: ['./testpage.component.scss'],
})
export class TestpageComponent implements AfterViewInit {

    routes: Object[] = [
        {
            title: 'Inbox',
            route: '/',
            icon: 'email',
        }, {
            title: 'Snoozed',
            route: '/',
            icon: 'access_time',
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
                private _changeDetectorRef: ChangeDetectorRef) {

    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this.media.broadcast();
            this._changeDetectorRef.detectChanges();
        });
    }

}
