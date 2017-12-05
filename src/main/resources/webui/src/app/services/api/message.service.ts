import {Injectable} from "@angular/core";
import {ApiRequestService} from './api-request.service';
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

export interface messages {
    msgs: string[];
}

@Injectable()
export class MessageService{
    constructor(private apiRequestService: ApiRequestService) {
    }

    getMessages(): Observable<messages> {
        let retValue: Subject<any> = new Subject<any>();
        let allMsg: messages;
        this.apiRequestService.get('messages/inbox', undefined)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    debugger;
                    allMsg = {
                        "msgs": jsonResp.msgs,
                    };
                    retValue.next(allMsg);
                }

            }, error => {
            });
        return retValue;
    };
}