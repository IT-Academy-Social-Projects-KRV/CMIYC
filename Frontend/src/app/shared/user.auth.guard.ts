import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot,Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserAuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
     if (localStorage.getItem('scope') =='user') {
        console.log('user');
        return true;
      }else{
        window.alert("You don't have permission to view this page");
        return false;
      }
  }
}


/* }else if (localStorage.getItem('scope') =='admin') {
        console.log('admin');
      this.router.navigate(['/admin/allUsers']);
      return true;
    }else if(localStorage.getItem('scope') =='null'){
      console.log('logout');
      this.router.navigate(['/login']);
      return false;    }
*/
