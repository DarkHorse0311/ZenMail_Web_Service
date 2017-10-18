import {Injectable, Inject} from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';

import {Observable, Subject} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserInfoService, LoginInfoInStorage} from '../user-info.service';
import {ApiRequestService} from './api-request.service';

export interface RegisterServiceParams {
    username: string;
    password: string;
    email: string;
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
    register(username: string, email: string, password: string): boolean {
        let me = this;

        let bodyData: RegisterServiceParams = {
            "username": username,
            "email": email,
            "password": password,
        };
        let loginDataSubject: Subject<any> = new Subject<any>(); // Will use this subject to emit data that we want after ajax login attempt
        let loginInfoReturn: LoginInfoInStorage; // Object that we want to send back to Login Page

        this.apiRequest.post('user', bodyData)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    //Create a success object that we want to send back to login page
                    /*
                    loginInfoReturn = {
                        "success": true,
                        "message": jsonResp.operationMessage,
                        "landingPage": this.landingPage,
                        "user": {
                            "userId": jsonResp.item.userId,
                            "email": jsonResp.item.emailAddress,
                            "displayName": jsonResp.item.firstName + " " + jsonResp.item.lastNameName,
                            "token": jsonResp.item.token,
                        }
                        /**/
                }
                ;

                // store username and jwt token in session storage to keep user logged in between page refreshes
                //this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
            },
        //loginDataSubject.next(loginInfoReturn);
        return true;
    };

    /*error2 => {
        loginInfoReturn = {
            "success": false,
            "message": error2.status,
            "landingPage": "/login"
        };
        loginDataSubject.next(loginInfoReturn);
    });
    */
}