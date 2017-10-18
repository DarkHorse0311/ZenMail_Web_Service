import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/api/login.service';
import {Router} from '@angular/router';
import {RegisterService} from "../../services/api/register.service";

@Component({
    templateUrl: './register.component.html',
    styleUrls: ['./register.scss'],
})

export class RegisterComponent implements OnInit {
    model: any = {};
    errMsg: string = '';

    constructor(private router: Router,
                private registerService: RegisterService) {
    }

    ngOnInit() {
    }

    register() {
        let success = false;
        success = this.registerService.register(this.model.name,this.model.email,this.model.password);

    }



}
