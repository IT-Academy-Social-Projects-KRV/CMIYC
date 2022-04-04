import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';
import {AbstractAuthGuard} from "./abstract.auth.guard";

@Injectable({
  providedIn: 'root'
})
export class UserAdminAuthGuard extends AbstractAuthGuard {
  constructor(private authService: AuthService, router: Router) {
    super(router, () => authService.isUserAdminLoggedIn(), "login");
  }
}
