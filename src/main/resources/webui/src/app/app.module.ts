import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

//Third Party Modules
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {ClarityModule} from 'clarity-angular';

//Local App Modules
import {AppRoutingModule} from './app-routing.module';


//Pages  -- Pages too are components, they contain other components
import {AppComponent} from './app.component';

// Services
import {AppConfig} from './app-config';


@NgModule({

    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,

        // Thirdparty Module
        NgxDatatableModule,
        NgxChartsModule,
        ClarityModule.forChild(),

        // Local App Modules
        AppRoutingModule
    ],

    declarations: [
        AppComponent,
    ],

    providers: [
        AppConfig,
    ],

    bootstrap: [AppComponent]
})

export class AppModule {
}
