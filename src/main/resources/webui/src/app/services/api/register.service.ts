import {Injectable, Inject} from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';

import {Observable, Subject} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserInfoService, LoginInfoInStorage} from '../user-info.service';
import {ApiRequestService} from './api-request.service';

export interface RegisterServiceParams {
    userId: string;
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
    register(userId: string, email: string, password: string): boolean {
        let me = this;
        let bodyData: RegisterServiceParams = {
            "userId": userId,
            "email": email,
            "password": password,
        };
        let success = false;
        this.apiRequest.post('user', bodyData)
            .subscribe(jsonResp => {
                console.log(jsonResp);
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    success = true;
                }
                // store username and jwt token in session storage to keep user logged in between page refreshes
                //this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
            }, error2 => {
            });
        console.log(success);
        return success;
    }

    //loginDataSubject.next(loginInfoReturn);

}