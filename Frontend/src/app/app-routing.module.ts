import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";
import {RegisterComponent} from "./register/register.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {TwoFactorAuthenticationFormComponent} from "./two-factor-authentication-form/two-factor-authentication-form.component";
import { UserSearchInterfaceComponent } from './user-search-interface/user-search-interface.component';
import { SearchResponseComponent } from './search-response/search-response.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'allSchemas', component: AdminManageSchemaListComponent },
  { path: 'allUsers', component: AdminManageUserListComponent },
  { path: "register", component: RegisterComponent },
  { path: "login", component: LoginFormComponent },
  { path: "twoFactor", component: TwoFactorAuthenticationFormComponent },
  { path: "register", component: RegisterComponent },
  { path: 'app-user-search-interface', component: UserSearchInterfaceComponent, outlet: 'routeUserSearch' },
  { path: 'app-search-response', component: SearchResponseComponent, outlet: 'routeUserSearch'},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
