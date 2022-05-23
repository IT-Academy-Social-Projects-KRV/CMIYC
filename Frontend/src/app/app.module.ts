import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegisterComponent} from './register/register.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {HeaderComponent} from './header/header.component';
import {Error403Component} from './errorpages/error403/error403.component';
import {Error404Component} from './errorpages/error404/error404.component';
import {Error500Component} from './errorpages/error500/error500.component';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminUserFormComponent} from "./admin-user-form/admin-user-form.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";
import {LoginFormComponent} from './login-form/login-form.component';
import {
  TwoFactorAuthenticationFormComponent
} from './two-factor-authentication-form/two-factor-authentication-form.component';
import {UserSearchInterfaceComponent} from './user-search-interface/user-search-interface.component';
import {SearchResponseComponent} from './search-response/search-response.component';
import {UserAuthGuard} from './shared/auth-guards/user.auth.guard';
import {UserAdminAuthGuard} from './shared/auth-guards/user.admin.auth.guard';
import {AuthService} from './shared/auth.service';
import {HttpClientModule} from '@angular/common/http';
import {
  AdminUserIsActiveToggleComponent
} from "./admin-user-is-active-toggle/admin-user-is-active-toggle-component";
import {AdminViewSchemaComponent} from "./admin-view-schema/admin-view-schema-component";
import {EnvService} from "./shared/env.service";
import {JsonFormComponent} from "./json-form/json-form-component";
import {NgxPaginationModule} from "ngx-pagination";
import {AdminUserUpdateFormComponent} from './admin-user-update-form/admin-user-update-form.component';
import {SpinnerComponent} from './spinner/spinner.component';
import {RECAPTCHA_SETTINGS, RecaptchaModule, RecaptchaSettings} from "ng-recaptcha";

const appInitializerFn = (appConfig: EnvService) => {
  return () => {
    return appConfig.loadAppConfig();
  }
};


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
    AdminUserIsActiveToggleComponent,
    AdminViewSchemaComponent,
    LoginFormComponent,
    TwoFactorAuthenticationFormComponent,
    UserSearchInterfaceComponent,
    SearchResponseComponent,
    AdminUserIsActiveToggleComponent,
    AdminUserUpdateFormComponent,
    JsonFormComponent,
    SpinnerComponent

  ],
  imports: [
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserModule,
    NgxPaginationModule,
    FormsModule,
    RecaptchaModule
  ],
  providers: [
    AuthService,
    UserAuthGuard,
    UserAdminAuthGuard,
    EnvService, {
      provide: RECAPTCHA_SETTINGS,
      useValue: {
        siteKey: "6LcxlA8gAAAAAJnRPMXjXqBHIAzluY9g-CkWAxH8",
      } as RecaptchaSettings,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFn,
      multi: true,
      deps: [EnvService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
