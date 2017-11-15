import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/api/login.service';
import {Router} from '@angular/router';
import {RegisterService} from "../../services/api/register.service";
import {Observable} from "rxjs/Observable";
import {_dom} from "@angular/flex-layout/utils/testing/dom-tools";

@Component({
    templateUrl: './register.component.html',
    styleUrls: ['./register.scss'],
})

export class RegisterComponent implements OnInit {
    model: any = {};
    errMsg: string = '';
    domain: string = '@zenmail.space'

    constructor(private router: Router,
                private registerService: RegisterService,
                private loginService: LoginService) {
    }

    ngOnInit() {
    }

    register() {
        if (!this.validatePassword()) {
            this.errMsg = 'passwords does not match';
            return;
        }
        if (!this.validateTermsAgreeCheckBox()) {
            this.errMsg = 'you don not agree with terms';
            return;
        }
        if (!this.validateId()) {
            this.errMsg = 'Unacceptable symbols in name\nUse only a-z,A-Z,.-_';
            return;
        }
        debugger;
        var fullUserName = this.model.username.concat(this.domain);

        debugger;
        this.registerService.register(fullUserName, this.model.name, this.model.password)
            .subscribe(resp => {

                    debugger;
                    if (resp === undefined || resp.success !== true || resp.success === undefined) {
                        this.errMsg = 'User already exist';
                        return;
                    }

                    if (!resp.success) {
                        console.log('switching message #' + resp.message);
                        switch (resp.message) {
                            case 1:
                                this.errMsg = 'Registration error';
                                break;
                            case 401:
                                this.errMsg = 'Username is already exist';
                                break;
                            case 404:
                                this.errMsg = 'Service not found';
                                break;
                            case 408:
                                this.errMsg = 'Request was Timed out';
                                break;
                            case 500:
                                this.errMsg = 'Internal Server Error';
                                break;
                            default:
                                this.errMsg = 'Server Error';
                        }
                        this.ngOnInit();
                    } else {
                        debugger;
                        console.log('Going to landing page - ' + resp.landingPage);
                        this.loginRegisteredUser(fullUserName, this.model.password);
                    }
                }
            );
    }

    validateId(): boolean {
        var userName = this.model.username;
        var name = this.model.name
        if (/^[a-zA-Z0-9_.]*$/.test(userName) == false) {
            return false;
        }
        if (/^[a-zA-Z0-9_.]*$/.test(name) == false) {
            return false;
        }
        return true;
    }

    validatePassword(): boolean {
        if (this.model.password !== this.model.passconf) {
            return false;
        }
        return true;
    }

    validateTermsAgreeCheckBox(): boolean {
        if (!this.model) {
            return false;
        }
        return true;
    }

    loginRegisteredUser(userName: string, password: string) {
        debugger;
        this.loginService.getToken(userName, password)
            .subscribe(resp => {
                    if (resp.user === undefined || resp.user.token === undefined || resp.user.token === "INVALID") {
                        this.errMsg = 'Username or password is incorrect';
                        return;
                    }
                    if (!resp.success) {
                        console.log('switching message #' + resp.message);
                        switch (resp.message) {
                            case 401:
                                this.errMsg = 'Username or password is incorrect';
                                break;
                            case 404:
                                this.errMsg = 'Service not found';
                                break;
                            case 408:
                                this.errMsg = 'Request was Timed out';
                                break;
                            case 500:
                                this.errMsg = 'Internal Server Error';
                                break;
                            default:
                                this.errMsg = 'Server Error';
                        }
                        this.ngOnInit();
                    } else {
                        console.log('Going to landing page - ' + resp.landingPage);
                        this.router.navigate([resp.landingPage]);
                    }
                }
            );
    }


}
