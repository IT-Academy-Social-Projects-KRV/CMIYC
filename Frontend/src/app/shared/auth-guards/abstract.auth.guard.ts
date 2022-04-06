import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';

export abstract class AbstractAuthGuard implements CanActivate {
  private readonly redirectToSupplier: Function;

  protected constructor(private router: Router, private checkAccessFunction: Function, redirectTo: string | Function) {
    this.redirectToSupplier = typeof redirectTo !== "string" ? redirectTo : () => redirectTo;
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const accessible: boolean = this.checkAccessFunction.call(this);
    if(!accessible) {
      this.router.navigate([this.redirectToSupplier.call(this)]);
    }

    return accessible;
  }
}
