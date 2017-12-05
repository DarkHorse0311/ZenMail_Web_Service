import {Component, OnInit} from '@angular/core';
import {messages, MessageService} from "../../services/api/message.service";
import {templateJitUrl} from "@angular/compiler";
import {noUndefined} from "@angular/compiler/src/util";

@Component({
    selector: 'app-inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.scss']
})
export class InboxComponent implements OnInit {
    messages: any = [{}];

    constructor(private messageService: MessageService,) {
        debugger;
        this.messageService.getMessages()
            .subscribe(res => {
                    let msg = new Array();
                    res.msgs.forEach(item =>{
                        debugger;
                        if(item !== null && !(item.length < 25) && item !== undefined ){
                            item = item.substring(0,25);
                        }
                        msg.push({
                            sender: 'Martin Heidegger',
                            title: 'Destruktion',
                            body: item,
                            date: 'Jan 17, 16:20',
                        });
                    })
                console.log(msg);
                console.log(msg);
                    this.messages = msg;

                }
            )

    }

    ngOnInit() {

    }

}
