import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home.component';

import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';

import {AuthGuard} from './services/auth_guard.service';
import {PageNotFoundComponent} from './pages/404/page-not-found.component';
import {RegisterComponent} from './pages/register/register.component';
import {TestpageComponent} from './pages/testpage/testpage.component';

const routes: Routes = [
    //Important: The sequence of path is important as the router go over then in sequential manner
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthGuard],
        children: [  // Children paths are appended to the parent path
            {
                path: '', redirectTo: '/home', pathMatch: 'full'
            },
        ]
    },
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'test', component: TestpageComponent},




    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule],
    declarations: [PageNotFoundComponent]
})
export class AppRoutingModule {
}
