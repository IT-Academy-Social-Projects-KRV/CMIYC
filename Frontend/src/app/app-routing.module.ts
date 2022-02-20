import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";
import {RegisterComponent} from "./register/register.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {
  TwoFactorAuthenticationFormComponent
} from "./two-factor-authentication-form/two-factor-authentication-form.component";
import {Error403Component} from "./errorpages/error403/error403.component";
import {Error404Component} from "./errorpages/error404/error404.component";
import {Error500Component} from "./errorpages/error500/error500.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'allSchemas', component: AdminManageSchemaListComponent },
  { path: 'allUsers', component: AdminManageUserListComponent },
  { path: "register", component: RegisterComponent },
  { path: "login", component: LoginFormComponent },
  { path: "twoFactor", component: TwoFactorAuthenticationFormComponent },
  { path: "error403", component: Error403Component},
  { path: "error404", component: Error404Component},
  { path: "error500", component: Error500Component}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
