import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DialogPosition, MdDialogRef, MdTooltip} from "@angular/material";
import {TdTextEditorComponent} from '@covalent/text-editor';
import {MessageService} from "../../services/api/message.service";
import {InstantiateExpr} from "@angular/compiler";
import {inject} from "@angular/core/testing";
import {UserInfoService} from "../../services/user-info.service";


@Component({
    selector: 'app-message',
    templateUrl: './new_message.component.html',
    styleUrls: ['./new_message.component.scss']
})
export class NewMessageComponent implements AfterViewInit {
    isFullscreen = false;
    message: any;

    public userEmail: string = "";
    public userName: string = "";

    constructor(public dialogRef: MdDialogRef<AfterViewInit>, private messageService: MessageService, private userInfoService: UserInfoService) {
        this.message = {
            "to": "",
            "body": "",
            "subjecy": ""
        }
        this.userEmail = this.userInfoService.getUserEmail();
        this.userName = this.userInfoService.getUserName();
    }

    @ViewChild('textEditor') private _textEditor: TdTextEditorComponent;


    ngAfterViewInit(): void {

    }

    fullscreen(): void {
        this.dialogRef.updateSize('100%', '70%');
        this.isFullscreen = true;
    }

    exitFullscreen(): void {
        this.dialogRef.updateSize('70%', '70%');
        this.dialogRef.updatePosition(
            {
                top: (window.innerHeight / 2) - (window.innerHeight * 0.35) + 'px',
                left: (window.innerWidth / 2) - (window.innerWidth * 0.35) + 'px'
            });
        this.isFullscreen = false;
    }

    send(): void {
        this.messageService.sendMsg(this.message.to, this.message.subject, this.message.msg)
            .subscribe(res => {
                    this.dialogRef.close()
                }
            );
    }

}
