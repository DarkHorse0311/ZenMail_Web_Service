import {Component, OnInit} from '@angular/core';
import {inboxModel, message, MessageService} from "../../services/api/message.service";
import {DataStorageService} from "../../services/data-storege.service";
import {NavigationEnd, Route, Router} from "@angular/router";
import {RootData} from "@angular/core/src/view";

@Component({
    selector: 'app-inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.scss']
})
export class InboxComponent implements OnInit {
    messages: any = [{}];

    constructor(private messageService: MessageService, private dataStorage: DataStorageService, private router: Router) {


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
        )
        if (!this.dataStorage.isEmptyMessages()) {
            this.messages = this.dataStorage.getMessages();
            return;
        }
        this.messageService.getMessages()
            .subscribe(res => {
                    let msg = new Array();
                    this.dataStorage.setData(res.msgs);
                    res.msgs.forEach(item => {
                        if (item !== null && item !== undefined) {
                            msg.push({
                                sender: item.from,
                                title: item.subject,
                                body: item.msg,
                                date: item.time,
                                id: item.id.replace('<', '').replace('>', '')
                            });
                        }

                    })
                    this.dataStorage.setMessages(msg);
                    this.messages = msg;
                }
            )
    }

}
