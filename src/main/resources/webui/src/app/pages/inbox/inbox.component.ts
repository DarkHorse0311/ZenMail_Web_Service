import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MessageService} from "../../services/api/message.service";
import {DataStorageService} from "../../services/data-storege.service";
import {NavigationEnd, Router} from "@angular/router";
import {LoadingMode, LoadingType, TdLoadingService} from '@covalent/core';
import {SearchService} from "../../services/api/search.service";
import {isNullOrUndefined} from "@swimlane/ngx-datatable/release/utils";

@Component({
    selector: 'app-inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.scss']
})
export class InboxComponent implements OnInit {
    messages: any = [{}];

    @Input() searchString: string;

    constructor(private messageService: MessageService,
                private dataStorage: DataStorageService,
                private router: Router,
                private loadingService: TdLoadingService,
                private searchService: SearchService) {
        loadingService.create({
            name: 'loadMessages',
            mode: LoadingMode.Indeterminate,
            type: LoadingType.Linear,
            color: 'accent',
        });

        this.searchService.stringSearched$.subscribe(msg => this.search(msg));
    }

    ngOnInit() {
        this.messages = null;
        this.router.events.subscribe(
            x => {
                if (x instanceof NavigationEnd) {
                    if (x.toString().match("message/.*")) {
                        let a = x.urlAfterRedirects.split('/').pop();
                        a = decodeURIComponent(decodeURIComponent(a));
                        console.log(x.urlAfterRedirects.split('/').pop());
                        let data = this.messages.find(val => val.id === a);
                        this.dataStorage.setData(data);
                    }
                }
            }
        );
        if (!this.dataStorage.isEmptyMessages()) {
            this.messages = this.dataStorage.getMessages();
            return;
        }
        this.loadingService.register('loadMessages');

        this.messageService.getMessages()
            .subscribe(res => {
                    let msg = [];
                    this.dataStorage.setData(res.msgs);
                    res.msgs.forEach(item => {
                        if (item !== null && item !== undefined) {
                            msg.push({
                                sender: item.from,
                                title: item.subject,
                                body: item.msg,
                                date: item.time,
                                id: item.id.replace('<', '').replace('>', ''),
                                visible: true,
                            });
                        }

                    });
                    this.dataStorage.setMessages(msg);
                    this.messages = msg;

                    this.loadingService.resolve('loadMessages');
                }
            );
    }

    private search(msg : string): void {
        if (isNullOrUndefined(msg))
        {
            console.log('empty');

        } else {

            console.log(msg);
            for (let i = 0; i < this.messages.length; i++)
            {
                let title : string = this.messages[i].title;
                let sender : string = this.messages[i].sender;

                if (title.search(msg) != -1 || sender.search(msg) != -1)
                {
                    this.messages[i].visible = true;

                } else {
                    this.messages[i].visible = false;
                }
            }

        }
    }


}
