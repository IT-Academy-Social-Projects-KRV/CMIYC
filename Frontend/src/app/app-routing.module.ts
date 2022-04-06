import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";
import {RegisterComponent} from "./register/register.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {Error403Component} from "./errorpages/error403/error403.component";
import {Error404Component} from "./errorpages/error404/error404.component";
import {Error500Component} from "./errorpages/error500/error500.component";
import {
  TwoFactorAuthenticationFormComponent
} from "./two-factor-authentication-form/two-factor-authentication-form.component";
import {UserSearchInterfaceComponent} from './user-search-interface/user-search-interface.component';
import {SearchResponseComponent} from './search-response/search-response.component';
import {UserAuthGuard} from './shared/auth-guards/user.auth.guard';
import {UserAdminAuthGuard} from './shared/auth-guards/user.admin.auth.guard';
import {SchemaAdminAuthGuard} from "./shared/auth-guards/schema.admin.auth.guard";
import {UnauthorizedAuthGuard} from "./shared/auth-guards/unauthorized.auth.guard";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'admin/allSchemas', component: AdminManageSchemaListComponent, canActivate: [SchemaAdminAuthGuard]},
  {path: 'admin/allUsers', component: AdminManageUserListComponent, canActivate: [UserAdminAuthGuard]},
  {path: "login", component: LoginFormComponent, canActivate: [UnauthorizedAuthGuard]},
  {path: "twoFactor", component: TwoFactorAuthenticationFormComponent, canActivate: [UnauthorizedAuthGuard]},
  {path: "error403", component: Error403Component},
  {path: "error404", component: Error404Component},
  {path: "error500", component: Error500Component},
  {path: "register", component: RegisterComponent},
  {path: 'userSearch', component: UserSearchInterfaceComponent, canActivate: [UserAuthGuard]},
  {path: 'searchResponse', component: SearchResponseComponent},
  {path:'**',component:Error404Component}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [UserAuthGuard, UserAdminAuthGuard]
})
export class AppRoutingModule {
}
