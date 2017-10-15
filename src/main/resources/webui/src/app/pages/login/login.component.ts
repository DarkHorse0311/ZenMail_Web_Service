import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/api/login.service';
import {Router} from '@angular/router';

@Component({
    selector: 's-login-pg',
    templateUrl: './login.component.html',
    styleUrls: ['./login.scss'],
})

export class LoginComponent implements OnInit {
    model: any = {};
    errMsg: string = '';

    constructor(private router: Router,
                private loginService: LoginService) {
    }

    ngOnInit() {
        // reset login status
        this.loginService.logout(false);
    }

    login() {
        this.loginService.getToken(this.model.username, this.model.password)
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

    onSignUp() {
        this.router.navigate(['signup']);
    }


}
