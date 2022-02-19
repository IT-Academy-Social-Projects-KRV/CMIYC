import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminUserFormComponent} from "./admin-user-form/admin-user-form.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    HeaderComponent,
    AdminManageSchemaListComponent,
    AdminUserFormComponent,
    AdminCreateSchemaFormComponent,
    AdminManageUserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
