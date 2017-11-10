import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

//Third Party Modules
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {NgxChartsModule} from '@swimlane/ngx-charts';

import {CovalentLayoutModule, CovalentStepsModule /*, any other modules */} from '@covalent/core';
// (optional) Additional Covalent Modules imports
import {CovalentHttpModule} from '@covalent/http';
import {CovalentHighlightModule} from '@covalent/highlight';
import {CovalentMarkdownModule} from '@covalent/markdown';
import {CovalentDynamicFormsModule} from '@covalent/dynamic-forms';
import { CovalentTextEditorModule } from '@covalent/text-editor';

//Local App Modules
import {AppRoutingModule} from './app-routing.module';
import {SharedModule} from './shared/shared.module';

// Components
import {LogoComponent} from './components/logo/logo.component';

//Pages  -- Pages too are components, they contain other components
import {AppComponent} from './app.component';
import {HomeComponent} from './home.component';
import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {RegisterComponent} from './pages/register/register.component';
import {TestpageComponent} from './pages/testpage/testpage.component';
import {NewMessageComponent} from './pages/new_message/new_message.component';
import { InboxComponent } from './pages/inbox/inbox.component';

// Services
import {AppConfig} from './app-config';
import {UserInfoService} from './services/user-info.service';
import {AuthGuard} from './services/auth_guard.service';
import {ApiRequestService} from './services/api/api-request.service';
import {LoginService} from './services/api/login.service';
import {RegisterService} from "./services/api/register.service";

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {CommonModule} from '@angular/common';
import {MdCardModule} from '@angular/material';
import {MdMenuModule} from '@angular/material';
import {MdInputModule} from '@angular/material';
import {MdButtonModule} from '@angular/material';
import {MdListModule} from '@angular/material';
import {MdIconModule} from '@angular/material';
import {MdSidenavModule} from '@angular/material';
import {MdToolbarModule} from '@angular/material';
import {MdTooltipModule} from '@angular/material';
import {MdDialogModule} from '@angular/material';
import {CovalentMediaModule} from '@covalent/core';
import {CovalentSearchModule} from '@covalent/core';
import {CovalentMenuModule} from '@covalent/core';
import {CovalentNotificationsModule} from '@covalent/core';
import { MessageComponent } from './pages/message/message.component';


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

        CovalentLayoutModule,
        CovalentStepsModule,

        // Local App Modules
        AppRoutingModule,
        SharedModule,

        CommonModule,
        MdCardModule,
        MdMenuModule,
        MdInputModule,
        MdButtonModule,
        MdListModule,
        MdIconModule,
        MdSidenavModule,
        MdToolbarModule,
        MdTooltipModule,
        MdDialogModule,
        CovalentLayoutModule,
        CovalentMediaModule,
        CovalentSearchModule,
        CovalentMenuModule,
        CovalentNotificationsModule,
        CovalentHttpModule.forRoot(),
        CovalentTextEditorModule,
    ],

    declarations: [
        // Components
        LogoComponent,

        //Pages -- Pages too are components, they contain other components
        AppComponent,
        HomeComponent,
        LoginComponent,
        LogoutComponent,
        TestpageComponent,
        RegisterComponent,
        NewMessageComponent,
        InboxComponent,
        MessageComponent
    ],

    providers: [
        AuthGuard,
        UserInfoService,
        ApiRequestService,
        LoginService,
        RegisterService,
        AppConfig,
    ],

    bootstrap: [AppComponent]
})

export class AppModule {
}
