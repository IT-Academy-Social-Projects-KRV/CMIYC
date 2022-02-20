import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { TwoFactorAuthenticationFormComponent } from './two-factor-authentication-form/two-factor-authentication-form.component';
import { UserSearchInterfaceComponent } from './user-search-interface/user-search-interface.component';
import { SearchResponseComponent } from './search-response/search-response.component';
import { NavUserSearchComponent } from './nav-user-search/nav-user-search.component';



@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    SidebarComponent,
    HeaderComponent,
    LoginFormComponent,
    TwoFactorAuthenticationFormComponent,
    UserSearchInterfaceComponent,
    SearchResponseComponent,
    NavUserSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
