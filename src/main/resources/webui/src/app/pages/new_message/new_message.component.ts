import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DialogPosition, MdDialogRef} from "@angular/material";
import {TdTextEditorComponent} from '@covalent/text-editor';


@Component({
    selector: 'app-message',
    templateUrl: './new_message.component.html',
    styleUrls: ['./new_message.component.scss']
})
export class NewMessageComponent implements AfterViewInit {
    isFullscreen = false;

    constructor(public dialogRef: MdDialogRef<AfterViewInit>) {
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

}
