import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/api/login.service';
import {Router} from '@angular/router';

@Component({
    templateUrl: './register.component.html',
    styleUrls: ['./register.scss'],
})

export class RegisterComponent implements OnInit {
    model: any = {};
    errMsg: string = '';

    constructor(private router: Router,
                private loginService: LoginService) {
    }

    ngOnInit() {
    }

    register() {
    }


}
