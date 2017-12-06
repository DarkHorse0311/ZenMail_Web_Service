import { Injectable } from "@angular/core";

@Injectable()
export class DataStorageService {
    public storage: any;
    public allMessages: any;

    isEmptyOneMsg(): boolean {
        return this.storage === null || this.storage === undefined;
    }

    isEmptyMessages(): boolean {
        return this.allMessages === null || this.allMessages === undefined;
    }

    setData(data : any){
        this.storage = data;
    }

    getData() : any{
        return this.storage;
    }

    getMessages(): any{
        return this.allMessages;
    }

    setMessages(date : any):any{
        this.allMessages = date;
    }
    constructor() {}
}