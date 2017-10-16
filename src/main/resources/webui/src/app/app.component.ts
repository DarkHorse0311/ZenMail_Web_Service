import {Component} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MdIconRegistry } from '@angular/material';

@Component({
    selector: 'app-root',
    template: `
        <router-outlet></router-outlet>`
})

export class AppComponent {

    constructor(private _iconRegistry: MdIconRegistry,
                private _domSanitizer: DomSanitizer) {
        this._iconRegistry.addSvgIconInNamespace('assets', 'covalent',
            this._domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/covalent.svg'));
        this._iconRegistry.addSvgIconInNamespace('assets', 'rsa',
            this._domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/rsa.svg'));
        this._iconRegistry.addSvgIconInNamespace('assets', 'github',
            this._domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/github.svg'));
    }
}
