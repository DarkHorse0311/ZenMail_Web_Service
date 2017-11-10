import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.scss']
})
export class InboxComponent implements OnInit {
    messages: Object[] = [
        {
            sender: 'Friedrich Nietzsche',
            title: 'God is dead',
            body: 'I\'m not upset that you lied to me, I\'m upset that from now on I can\'t believe you',
            date: 'Oct 24, 10:23',
        }, {
            sender: 'Jean-Paul Sartre',
            title: 'Existence precedes essence',
            body: 'She believed in nothing. Only her scepticism kept her from being an atheist',
            date: 'Apr 15, 14:37',
        }, {
            sender: 'Martin Heidegger',
            title: 'Destruktion',
            body: 'Making itself intelligible is suicide for philosophy',
            date: 'Jan 17, 16:20',
        }, {
            sender: 'Sergio Kostion',
            title: 'Baguette',
            body: 'Work for slaves -- not for Übermensch like me',
            date: 'Nov 7, 7:06',
        }
    ];

    constructor() {
    }

    ngOnInit() {
    }

}
