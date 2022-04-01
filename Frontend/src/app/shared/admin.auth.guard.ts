import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isUserAdminLoggedIn()) {
      console.log('admin');
      return true;
    } else if (this.authService.isSchemaAdminLoggedIn()) {
      return true;
    } else {
      this.authService.performLogout();
      window.alert("You don't have permission to view this page");
      return false;
    }
  }
}
