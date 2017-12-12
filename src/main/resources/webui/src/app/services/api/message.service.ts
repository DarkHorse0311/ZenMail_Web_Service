import {Injectable} from "@angular/core";
import {ApiRequestService} from './api-request.service';
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

export interface message {
    subject: string;
    msg: string;
    from: string;
    id: string;
    time: string;
}

interface outMessage {
    msg: string,
    to: string,
    subject: string,
}

export interface inboxModel {
    operationStatus: string;
    operationMessage: string;
    msgs: message[];
}

@Injectable()
export class MessageService {
    constructor(private apiRequestService: ApiRequestService) {
    }

    getMessages(): Observable<inboxModel> {
        let retValue: Subject<any> = new Subject<any>();
        let allMsg: inboxModel;
        this.apiRequestService.get('messages/inbox', undefined)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    allMsg = {
                        "operationStatus": jsonResp.operationStatus,
                        "operationMessage": jsonResp.operationMessage,
                        "msgs": jsonResp.msgs
                    };
                    retValue.next(allMsg);
                }

            }, error => {
            });
        return retValue;
    };

    getMessageById(id: string): Observable<inboxModel> {
        let retValue: Subject<any> = new Subject<any>();
        let allMsg: inboxModel;
        this.apiRequestService.get('messages/inbox/' + id, undefined)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    allMsg = {
                        "operationStatus": jsonResp.operationStatus,
                        "operationMessage": jsonResp.operationMessage,
                        "msgs": jsonResp.msgs
                    };
                    retValue.next(allMsg);
                }

            }, error => {
            });
        return retValue;
    }

    sendMsg(to: string, title: string, body: string): Observable<any> {
        let msg: outMessage;
        msg = {
            msg: body,
            subject: title,
            to: to,
        };
        let retValue: Subject<any> = new Subject<any>();
        this.apiRequestService.post('messages', msg)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    let res: any;
                    res = {
                        "operationStatus": jsonResp.operationStatus,
                        "operationMessage": jsonResp.operationMessage
                    };
                    retValue.next(res)
                }

            }, error => {
            });
        return retValue;
    }
}