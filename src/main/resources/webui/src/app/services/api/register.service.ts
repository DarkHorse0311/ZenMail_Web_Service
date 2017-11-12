import {Injectable, Inject} from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';


import {Observable, Subject} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserInfoService, LoginInfoInStorage, RegisterInfoStorage} from '../user-info.service';
import {ApiRequestService} from './api-request.service';

export interface RegisterServiceParams {
    username: string;
    password: string;
    name: string;
}

@Injectable()
export class RegisterService {
    public landingPage: string = "/home";

    constructor(private router: Router,
                private http: Http,
                private userInfoService: UserInfoService,
                private apiRequest: ApiRequestService) {
    }

    // noinspection JSAnnotator
    register(userId: string, name: string, password: string): Observable<any> {
        let me = this;
        let bodyData: RegisterServiceParams = {
            "username": userId,
            "name": name,
            "password": password,
        };
        let registerDataSubject: Subject<any> = new Subject<any>(); // Will use this subject to emit data that we want after ajax login attempt
        let registerInfoReturn: RegisterInfoStorage; // Object that we want to send back to Login Page

        this.apiRequest.post('user', bodyData)
            .subscribe(jsonResp => {
                console.log(jsonResp);
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    registerInfoReturn = {
                        "success": true,
                        "message": jsonResp.operationMessage,
                        "landingPage": this.landingPage,
                    }
                }
                registerDataSubject.next(registerInfoReturn);
                // store username and jwt token in session storage to keep user logged in between page refreshes
                //this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
            }, error2 => {
                registerInfoReturn = {
                    "success": false,
                    "message": error2.status,
                    "landingPage": "/register",
                };
                registerDataSubject.next(registerInfoReturn);
            });
        return registerDataSubject;
    }

    //loginDataSubject.next(loginInfoReturn);

}