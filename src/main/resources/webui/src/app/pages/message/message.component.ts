import {Component, OnInit} from '@angular/core';
import {DataStorageService} from "../../services/data-storege.service";
import {MessageService} from "../../services/api/message.service";
import {Router} from "@angular/router";
import {Location} from '@angular/common';

@Component({
    selector: 'app-message',
    templateUrl: './message.component.html',
    styleUrls: ['./message.component.scss']
})
export class MessageComponent implements OnInit {
    message: any;

    public senderAvatar: any = {
        size: 40, // default size is 100
        fontColor: '#FFFFFF',
        border: "2px solid #d3d3d3",
        isSquare: false, // if it is true then letter avatar will be in square defaule value is false
        text: "", //
        fixedColor:true //if you enable true then letter will have same color for ever default value is false
    };


    constructor(private messageService: MessageService,
                private dataStorage: DataStorageService,
                private  route: Router,
                private location: Location) {
    }

    ngOnInit() {
        let msg: any;
        this.message = {
            "title": "",
            "id": "",
            "sender": "",
            "date": "",
            "body": "",
        };
        if (this.dataStorage.isEmptyOneMsg()) {
            this.messageService.getMessageById(this.route.url.split('/').pop())
                .subscribe(res => {
                        msg = res.msgs[0];
                        console.log(this.route.url);
                        this.dataStorage.setData(msg);
                        this.message = {
                            "title": msg.subject,
                            "id": msg.id,
                            "sender": msg.from,
                            "date": msg.time,
                            "body": msg.msg,
                        };
                        this.dataStorage.setData(this.message);
                    }
                )
        } else {
            this.message = this.dataStorage.getData();
        }

        this.senderAvatar.text = this.message.sender;
    }
    back() : void {
        this.location.back();
    }

}
