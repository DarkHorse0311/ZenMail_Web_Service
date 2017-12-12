import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

@Injectable()
export class SearchService {

    private searchString = new Subject<string>();


    stringSearched$ = this.searchString.asObservable();

    constructor() {
    }

    search(str : string): void {
        this.searchString.next(str);
    }
}