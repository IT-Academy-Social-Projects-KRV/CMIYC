import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminManageSchemaListComponent } from "./admin-manage-schema-list/admin-manage-schema-list.component";
import { AdminManageUserListComponent } from "./admin-manage-user-list/admin-manage-user-list.component";
import { RegisterComponent } from "./register/register.component";
import { LoginFormComponent } from "./login-form/login-form.component";
import { Error403Component } from "./errorpages/error403/error403.component";
import { Error404Component } from "./errorpages/error404/error404.component";
import { Error500Component } from "./errorpages/error500/error500.component";
import { TwoFactorAuthenticationFormComponent } from "./two-factor-authentication-form/two-factor-authentication-form.component";
import { UserSearchInterfaceComponent } from './user-search-interface/user-search-interface.component';
import { SearchResponseComponent } from './search-response/search-response.component';
import { UserAuthGuard } from './shared/user.auth.guard';
import { AdminAuthGuard } from './shared/admin.auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'admin/allSchemas', component: AdminManageSchemaListComponent },
  { path: 'admin/allUsers', component: AdminManageUserListComponent , canActivate: [AdminAuthGuard]},
  { path: "login", component: LoginFormComponent },
  { path: "twoFactor", component: TwoFactorAuthenticationFormComponent },
  { path: "error403", component: Error403Component },
  { path: "error404", component: Error404Component },
  { path: "error500", component: Error500Component },
  { path: "register", component: RegisterComponent },
  { path: 'userSearch', component: UserSearchInterfaceComponent , canActivate: [UserAuthGuard] },
  { path: 'searchResponse', component: SearchResponseComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [UserAuthGuard, AdminAuthGuard]
})
export class AppRoutingModule { }
