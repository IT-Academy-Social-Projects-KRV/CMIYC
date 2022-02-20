import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { UserSearchInterfaceComponent } from './user-search-interface/user-search-interface.component';
import { SearchResponseComponent } from './search-response/search-response.component';

const routes: Routes = [
  { path: "register", component: RegisterComponent },
  { path: 'app-user-search-interface', component: UserSearchInterfaceComponent },
  { path: 'app-search-response', component: SearchResponseComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
