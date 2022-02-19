import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminManageSchemaListComponent} from "./admin-manage-schema-list/admin-manage-schema-list.component";
import {AdminCreateSchemaFormComponent} from "./admin-create-schema-form/admin-create-schema-form.component";
import {AdminManageUserListComponent} from "./admin-manage-user-list/admin-manage-user-list.component";

const routes: Routes = [
  { path: '', redirectTo: '/allSchemas', pathMatch: 'full' },
  { path: 'allSchemas', component: AdminManageSchemaListComponent },
  { path: 'allUsers', component: AdminManageUserListComponent },

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
