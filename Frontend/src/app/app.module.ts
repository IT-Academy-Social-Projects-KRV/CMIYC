import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { Error403Component } from './errorpages/error403/error403.component';
import { Error404Component } from './errorpages/error404/error404.component';
import { Error500Component } from './errorpages/error500/error500.component';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminUserFormComponent} from "./admin-user-form/admin-user-form.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";
import { LoginFormComponent } from './login-form/login-form.component';
import { TwoFactorAuthenticationFormComponent } from './two-factor-authentication-form/two-factor-authentication-form.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    SidebarComponent,
    HeaderComponent,
    Error403Component,
    Error404Component,
    Error500Component,
    AdminManageSchemaListComponent,
    AdminUserFormComponent,
    AdminCreateSchemaFormComponent,
    AdminManageUserListComponent,
    LoginFormComponent,
    TwoFactorAuthenticationFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
